#include "Feature.h"
#include "FileProcess.h"

using namespace std;
using namespace cv;
Feature::Feature()
{
}
void Feature::FeatureExtractAndCluster(string dir_path,string descriptorPath,string vocabularyPath)
{
    //--step 1:读取dir_path目录下的所有问价夹中的所有含有图片的文件夹
    vector<string> imgFullNameList;
    FileProcess fileProcess;
    imgFullNameList=fileProcess.ReadAllImage(dir_path);

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
        fileProcess.UnifyImageSize(img);
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


}
