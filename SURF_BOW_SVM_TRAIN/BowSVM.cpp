#include "BowSVM.h"
using namespace cv;
using namespace std;
BowSVM::BowSVM(string dir_path):
    detector(new SurfFeatureDetector(400)),
    matcher(new FlannBasedMatcher()),
    extractor(new OpponentColorDescriptorExtractor(Ptr<DescriptorExtractor>(new SurfDescriptorExtractor(400))))
{

    this->dir_path=dir_path;
    bowExtractor = Ptr<BOWImgDescriptorExtractor>(new BOWImgDescriptorExtractor(extractor,matcher));
    constractBoW();
}


void BowSVM::unifyImageSize(Mat &image)
{
    Mat unified_image;
    int s = cv::min(image.rows, image.cols);
    float scale = 128.0 / s;
    Size size(image.cols  * scale, image.rows * scale);
    cv::resize(image, unified_image, size);
    cv::medianBlur(unified_image, unified_image, 3);
    image = unified_image;

}

void BowSVM::readFolderName(vector<string>&folderNames)
{

    ifstream fin("..\\resource\\category.list");
   // ifstream fin(category_path);
    while(!fin.eof())
    {
        string folder;
        fin>>folder;
        folderNames.push_back(folder);
    }
}
void BowSVM::readAllImage(vector<string>&images)
{
    vector<string> folderlist;
    this->readFolderName(folderlist);
    for(vector<string>::size_type i=0;i<folderlist.size();i++)
    {
        string folder=folderlist[i];
        string path=dir_path+folder;
        Directory dir;
        vector<string> imgNames=dir.GetListFiles(path,"*.jpg",false);
        for(vector<string>::size_type j=0;j<imgNames.size();j++)
        {
            string imgName=imgNames[j];
            string fullName=path+"\\"+imgName;
            images.push_back(fullName);
        }
    }

}
void BowSVM::calSurf(Mat&descriptors,string fileName)
{

    vector<KeyPoint> keyPoints;
    Mat img=imread(fileName);
    unifyImageSize(img);
    detector->detect(img,keyPoints);
    extractor->compute(img,keyPoints,descriptors);
}

void BowSVM::featureExtract()
{
    //--step 1:读取dir_path目录下的所有问价夹中的所有含有图片的文件夹
    vector<string> imgFullNameList;
    readAllImage(imgFullNameList);
    //--step 2：统一图片大小，并提取图片特征，放入allDescriptors中。
    for(vector<string>::size_type k=0;k<imgFullNameList.size();k++)
    {
        Mat descriptors;
        calSurf(descriptors,imgFullNameList[k]);
        allDescriptors.push_back(descriptors);

    }
}
void BowSVM::saveFeature(string descriptorPath)
{
    //--step 3:feature聚类并将所有descriptor和vocabulary存入文件
    FileStorage fin_Descriptor (descriptorPath,FileStorage::WRITE);
    fin_Descriptor<<"allDescriptors"<<allDescriptors;
    fin_Descriptor.release();
}

void BowSVM::featureCluster()
{
    BOWKMeansTrainer bowtrainer(500);//样例取的1000
    bowtrainer.add(allDescriptors);
    vocabulary=bowtrainer.cluster();


}
void BowSVM::saveCluster(string vocabularyPath)
{
    FileStorage fin_Vocabulary(vocabularyPath,FileStorage::WRITE);
    fin_Vocabulary<<"vocabulary"<<vocabulary;
    fin_Vocabulary.release();
}

void BowSVM::constractBoW()
{
    featureExtract();
    //saveFeature(,descriptorPath);
    featureCluster();
    //saveCluster(vocabularyPath);
    bowExtractor->setVocabulary(vocabulary);
    matcher->train();
    vector<string> folderlist;
    readFolderName(folderlist);
    for(vector<string>::size_type i=0;i<folderlist.size();i++)
    {
        string folder=folderlist[i];
        string path=dir_path+folder;
        Directory dir;
        vector<string> imgNames=dir.GetListFiles(path,"*.jpg",false);
        for(vector<string>::size_type j=0;j<imgNames.size();j++)
        {
            string imgName=imgNames[j];
            string fullName=path+"\\"+imgName;
            cout<<fullName<<endl;
            Mat descriptors;
            calSurf(descriptors,fullName);
            if(samples.count(folder)==0)
                samples[folder].create(0,descriptors.cols,descriptors.type());
            samples[folder].push_back(descriptors);
        }
    }
}
void BowSVM::saveSamples(string samplePath)
{
    FileStorage fin (samplePath,FileStorage::WRITE);
    map<string,Mat>::iterator iter;
    for(iter=samples.begin();iter!=samples.end();++iter)
    {
        fin<<"lable"+ iter->first<<iter->second;
        cout<<iter->first<<endl;
    }
    fin.release();
}
void BowSVM::prepareSample(Mat &allSamples, Mat &lables, string category)
{

    //copy category sample and lable;

    allSamples.push_back(samples[category]);
    Mat posResponses=Mat::ones(samples[category].rows,1,CV_32FC1);
    lables.push_back(posResponses);
    //copy the rest sample and lable
    map<string,Mat>::iterator iter1;
    for(iter1=samples.begin();iter1!=samples.end();++iter1)
    {
        string not_category=iter1->first;
        if(not_category==category)
            continue;
        allSamples.push_back(samples[not_category]);
        posResponses=-1*Mat::ones(samples[not_category].rows,1,CV_32FC1);
        lables.push_back(posResponses);
    }


}

void BowSVM::train()
{
    int cols=bowExtractor->descriptorSize();
    int type=bowExtractor->descriptorType();
    vector<string> categoryList;
    for(map<string,Mat>::iterator iter=samples.begin();iter!=samples.end();++iter)
    {
        categoryList.push_back(iter->first);
    }
    CvSVMParams svmParams;
    svmParams.svm_type=CvSVM::C_SVC;
    svmParams.kernel_type=CvSVM::RBF;
    svmParams.C=5;
    svmParams.gamma=0.1;
    svmParams.term_crit.epsilon=1e-8;
    svmParams.term_crit.max_iter=1e9;
    svmParams.term_crit.type=CV_TERMCRIT_ITER | CV_TERMCRIT_EPS;
    //one vs all method
    for (vector<string>::size_type i=0;i<categoryList.size();i++)
    {
        string category=categoryList[i];
        Mat allSamples(0,cols,type);
        Mat lables(0,1,CV_32FC1);
        prepareSample(allSamples,lables,category);
        //train and save
        if(allSamples.rows==0)
            continue;
        Mat sample_32f;
        allSamples.convertTo(sample_32f,CV_32F);

        CvSVM SVM;
        SVM.train(sample_32f,lables,Mat(),Mat(),svmParams);
        //save classifier
        string classifier_name("SVM_classifier_");
        classifier_name=dir_path+classifier_name+category+".yml";
        SVM.save(classifier_name.c_str());
    }
}


