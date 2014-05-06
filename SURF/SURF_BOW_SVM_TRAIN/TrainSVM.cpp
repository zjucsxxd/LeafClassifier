#include "TrainSVM.h"
using namespace cv;
using namespace std;
TrainSVM::TrainSVM()

{
    dir_path="picture\\";
    descriptorPath="picture\\allDescriptors.yml";
    vocabularyPath="picture\\vocabulary.yml";
    samplePath="picture\\samples.yml";
}
vector<string> TrainSVM::ReadFolderName()
{
    ifstream fin("picture\\category.list");
    vector <string> folderlist;
    while(!fin.eof())
    {
        string folder;
        fin>>folder;
        folderlist.push_back(folder);
    }
    return folderlist;
}
vector <string>TrainSVM::ReadAllImage()
{
    vector<string> imgFullNameList;
    vector<string> folderlist=this->ReadFolderName();
    for(int i=0;i<folderlist.size();i++)
    {
        string folder=folderlist[i];
        string path=dir_path+folder;
        Directory dir;
        vector<string> imgNames=dir.GetListFiles(path,"*.jpg",false);
        for(int j=0;j<imgNames.size();j++)
        {
            string imgName=imgNames[j];
            string fullName=path+"\\"+imgName;
            imgFullNameList.push_back(fullName);
        }
    }
    return imgFullNameList;

}

void TrainSVM::UnifyImageSize(cv::Mat& image)
{
    Mat unified_image;
    int s = cv::min(image.rows, image.cols);
    float scale = 128.0 / s;
    Size size(image.cols  * scale, image.rows * scale);
    cv::resize(image, unified_image, size);
    cv::medianBlur(unified_image, unified_image, 3);
    image = unified_image;

}
string TrainSVM::FeatureExtractAndCluster()
{
    //--step 1:读取dir_path目录下的所有问价夹中的所有含有图片的文件夹
    vector<string> imgFullNameList;
    imgFullNameList=this->ReadAllImage();

    //--step 2：统一图片大小，并提取图片特征，放入allDescriptors中。
    int minHessian=400;
    SurfFeatureDetector detector(minHessian);
    SurfDescriptorExtractor extractor;
    Mat descriptors;
    Mat allDescriptors;
    vector<KeyPoint> keyPoints;
    for(int k=0;k<imgFullNameList.size();k++)
    {
        Mat img=imread(imgFullNameList[k]);
        this->UnifyImageSize(img);
        detector.detect(img,keyPoints);
        extractor.compute(img,keyPoints,descriptors);
        allDescriptors.push_back(descriptors);
    }
    //--step 3:feature聚类并将所有descriptor和vocabulary存入文件
    FileStorage fin_Descriptor (descriptorPath,FileStorage::WRITE);
    fin_Descriptor<<"allDescriptors"<<allDescriptors;
    fin_Descriptor.release();
    BOWKMeansTrainer bowtrainer(500);//聚500类
    bowtrainer.add(allDescriptors);
    Mat vocabulary=bowtrainer.cluster();
    FileStorage fin_Vocabulary(vocabularyPath,FileStorage::WRITE);
    fin_Vocabulary<<"vocabulary"<<vocabulary;
    fin_Vocabulary.release();
    return vocabularyPath;
}
void TrainSVM::ConstractBoW(Ptr<FeatureDetector> &detector,BOWImgDescriptorExtractor &bowExtractor, map<string,Mat>& samples)
{

    vector<string> folderlist=this->ReadFolderName();
    vector<KeyPoint> keyPoints;
    Mat descriptors;
    for(int i=0;i<folderlist.size();i++)
    {
        string folder=folderlist[i];
        string path=dir_path+folder;
        Directory dir;
        vector<string> imgNames=dir.GetListFiles(path,"*.jpg",false);
        for(int j=0;j<imgNames.size();j++)
        {
            string imgName=imgNames[j];
            string fullName=path+"\\"+imgName;
            cout<<fullName<<endl;
            Mat img=imread(fullName);
            this->UnifyImageSize(img);

            detector->detect(img,keyPoints);
            bowExtractor.compute(img,keyPoints,descriptors);
            if(samples.count(folder)==0)
                samples[folder].create(0,descriptors.cols,descriptors.type());
            samples[folder].push_back(descriptors);
        }
    }
    FileStorage fin (samplePath,FileStorage::WRITE);
    map<string,Mat>::iterator iter;
    for(iter=samples.begin();iter!=samples.end();++iter)
    {
        fin<<"lable"+ iter->first<<iter->second;
        cout<<iter->first<<endl;
    }
    fin.release();
}
void TrainSVM::Train(map<string, Mat> &samples, int cols, int type)
{
    map<string,Mat>::iterator iter;
    vector<string>categoryList;
    for(iter=samples.begin();iter!=samples.end();++iter)
    {
        categoryList.push_back(iter->first);
    }
    //以下使用的是one vs all 方法。
    for (int i=0;i<categoryList.size();i++)
    {
        string category=categoryList[i];
        //--step 1：设置正样本标签为1
        Mat allsamples(0,cols,type);
        Mat lables(0,1,CV_32FC1);
        allsamples.push_back(samples[category]);
        Mat posResponses=Mat::ones(samples[category].rows,1,CV_32FC1);
        lables.push_back(posResponses);

        //--step2：设置负样本标签为-1
        map<string,Mat>::iterator iter1;
        for(iter1=samples.begin();iter1!=samples.end();++iter1)
        {
             string not_category=iter1->first;
             if(not_category==category)
                continue;
            allsamples.push_back(samples[not_category]);
            posResponses=-1*Mat::ones(samples[not_category].rows,1,CV_32FC1);
            lables.push_back(posResponses);
        }
        //--step3：设置SVM参数
        if(allsamples.rows==0)
            continue;
        Mat sample_32f;
        CvSVMParams svmParams;
        allsamples.convertTo(sample_32f,CV_32F);
        svmParams.svm_type=CvSVM::C_SVC;
        svmParams.kernel_type=CvSVM::RBF;
        svmParams.C=5;
        svmParams.gamma=0.1;
        svmParams.term_crit.epsilon=1e-8;
        svmParams.term_crit.max_iter=1e9;
        svmParams.term_crit.type=CV_TERMCRIT_ITER | CV_TERMCRIT_EPS;

        //--step4：训练SVM分类器并保存
        CvSVM SVM;
        SVM.train(sample_32f,lables,Mat(),Mat(),svmParams);
        string classifier_name("SVM_classifier_");
        classifier_name="picture\\"+classifier_name+category+".yml";
        SVM.save(classifier_name.c_str());
    }
}
