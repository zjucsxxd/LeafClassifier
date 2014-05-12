#include "TrainSVM.h"
#include "FileUtil.h"
using namespace cv;
using namespace std;
TrainSVM::TrainSVM(string dir_path):
    detector(new SurfFeatureDetector(400)),
    matcher(new FlannBasedMatcher()),
    extractor(new OpponentColorDescriptorExtractor(Ptr<DescriptorExtractor>(new SurfDescriptorExtractor(400))))
{

    this->dir_path=dir_path;
    bowExtractor = Ptr<BOWImgDescriptorExtractor>(new BOWImgDescriptorExtractor(extractor,matcher));

}
vector<string> TrainSVM::ReadFolderName()
{

    ifstream fin("..\\resource\\category.list");
   // ifstream fin(category_path);
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
void TrainSVM::FeatureExtractAndCluster(string descriptorPath,string vocabularyPath)
{
    //--step 1:读取dir_path目录下的所有问价夹中的所有含有图片的文件夹
    vector<string> imgFullNameList;
    FileUtil fileUtil;
    imgFullNameList=fileUtil.ReadAllImage(dir_path);
    //--step 2：统一图片大小，并提取图片特征，放入allDescriptors中。
    Mat descriptors;
    Mat allDescriptors;
    vector<KeyPoint> keyPoints;
    for(int k=0;k<imgFullNameList.size();k++)
    {
        Mat img=imread(imgFullNameList[k]);
        fileUtil.UnifyImageSize(img);
        detector->detect(img,keyPoints);
        extractor->compute(img,keyPoints,descriptors);
        allDescriptors.push_back(descriptors);

    }
    //--step 3:feature聚类并将所有descriptor和vocabulary存入文件
    FileStorage fin_Descriptor (descriptorPath,FileStorage::WRITE);
    fin_Descriptor<<"allDescriptors"<<allDescriptors;
    fin_Descriptor.release();
    BOWKMeansTrainer bowtrainer(500);//样例取的1000
    bowtrainer.add(allDescriptors);
    vocabulary=bowtrainer.cluster();
    FileStorage fin_Vocabulary(vocabularyPath,FileStorage::WRITE);
    fin_Vocabulary<<"vocabulary"<<vocabulary;
    fin_Vocabulary.release();

}
void TrainSVM::ConstractBoW(map<string, Mat> &samples)
{
    this->FeatureExtractAndCluster(descriptorPath,vocabularyPath);
    bowExtractor->setVocabulary(vocabulary);
    matcher->train();

    FileUtil fileUtil;
    vector<string> folderlist=fileUtil.ReadFolderName();
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
            fileUtil.UnifyImageSize(img);

            detector->detect(img,keyPoints);
            bowExtractor->compute(img,keyPoints,descriptors);
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
void TrainSVM::Train(map<string, Mat> &samples)
{
    int cols=bowExtractor->descriptorSize();
    int type=bowExtractor->descriptorType();
    map<string,Mat>::iterator iter;
    vector<string>categoryList;
    for(iter=samples.begin();iter!=samples.end();++iter)
    {
        categoryList.push_back(iter->first);
    }
    //one vs all method
    for (int i=0;i<categoryList.size();i++)
    {
        string category=categoryList[i];
        //copy category samples and lable;
        Mat allsamples(0,cols,type);
        Mat lables(0,1,CV_32FC1);
        allsamples.push_back(samples[category]);
        Mat posResponses=Mat::ones(samples[category].rows,1,CV_32FC1);
        lables.push_back(posResponses);
        //copy the rest samples and lable
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
        //train and save
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

        CvSVM SVM;
        SVM.train(sample_32f,lables,Mat(),Mat(),svmParams);
        //save classifier
        string classifier_name("SVM_classifier_");
        classifier_name=dir_path+classifier_name+category+".yml";
        SVM.save(classifier_name.c_str());
    }
}
float TrainSVM::ratio()
{
}

