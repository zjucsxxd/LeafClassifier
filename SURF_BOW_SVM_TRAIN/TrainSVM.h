#ifndef TRAINSVM_H
#define TRAINSVM_H
#include <opencv2/core/core.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/contrib/contrib.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/ml/ml.hpp>
#include <vector>
#include<string>
#include<fstream>
#include<iostream>
#include <map>
using namespace cv;
using namespace std;
class TrainSVM
{
private:
    Ptr<FeatureDetector > detector;
    Ptr<DescriptorMatcher > matcher;
    Ptr<DescriptorExtractor > extractor;
    Ptr<BOWImgDescriptorExtractor > bowExtractor;
    string dir_path;
    string descriptorPath="..\\resource\\allDescriptors.yml";
    string vocabularyPath="..\\resource\\vocabulary.yml";
    string samplePath="..\\resource\\samples.yml";
    Mat vocabulary;

    vector<string> ReadFolderName();
    vector<string> ReadAllImage();
    void UnifyImageSize(cv::Mat& image);
    void ReadVocabulary(string vocabularyPath);
    void FeatureExtractAndCluster(string descriptorPath,string vocabularyPath);

public:

    void ConstractBoW( map<string,Mat>& samples);
    void Train(map<string,Mat>& samples);
    TrainSVM(string dir_path);
    float ratio();

};

#endif // TRAINSVM_H
