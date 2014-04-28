#include <iostream>
#include <Feature.h>
#include <FileProcess.h>
#include <BagOfWord.h>
#include <TrainSVM.h>
#include <opencv2/core/core.hpp>
#include <string>
#include <vector>
#include <iostream>
#include <iomanip>
#include <sstream>
#include<fstream>
using namespace std;

int main()
{
    //--step 1:提取图片特征并聚类
    string dir_path="picture\\";
    string descriptorPath="picture\\allDescriptors.yml";
    string vocabularyPath="picture\\vocabulary.yml";
    Feature feature;
    feature.FeatureExtractAndCluster(dir_path,descriptorPath,vocabularyPath);

    //--step 2:读取“vocabulary.yml”，构造bag of word
    Mat vocabulary;
    FileStorage fs(vocabularyPath, FileStorage::READ);
    fs["vocabulary"] >> vocabulary;
    fs.release();
    Ptr<FeatureDetector> detector(new SurfFeatureDetector(400));
    Ptr<DescriptorExtractor> extractor( new SurfDescriptorExtractor(400));
    Ptr<DescriptorMatcher> matcher(new FlannBasedMatcher());
    BOWImgDescriptorExtractor bowExtractor(extractor, matcher);
    bowExtractor.setVocabulary(vocabulary);
    matcher->train();
    map<string, Mat> samples;
    BagOfWord bow;
    bow.ConstractBoW(detector,bowExtractor,samples);

    //--step 3:训练 SVM
    TrainSVM trainSVM;
    trainSVM.Train(samples,bowExtractor.descriptorSize(),bowExtractor.descriptorType());
    cout<<"~ hah , game over "<<endl;
    return 0;
}
