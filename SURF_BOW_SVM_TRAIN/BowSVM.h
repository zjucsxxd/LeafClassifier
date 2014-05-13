#ifndef TRAINSVM_H
#define TRAINSVM_H
#include <opencv2/opencv.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <vector>
#include<string>
#include<fstream>
#include<iostream>
#include <map>
using namespace cv;
using namespace std;
//TODO 变量名和函数名可以修改为更合适的名称
class BowSVM
{
private:
    Ptr<FeatureDetector > detector;
    Ptr<DescriptorMatcher > matcher;
    Ptr<DescriptorExtractor > extractor;
    Ptr<BOWImgDescriptorExtractor > bowExtractor;
    string dir_path;
    Mat vocabulary;
    Mat allDescriptors;
    map<string, Mat> samples;
    void readFolderName(vector<string>&folderNames);
    void readAllImage(vector<string>&images);
    void unifyImageSize(Mat& image);
    void calSurf(Mat&descriptors,string fileName);
    void featureExtract();
    void featureCluster();
    void constractBoW( );
    void prepareSample(Mat&allSamples, Mat&lables, string category);
public:
    void train();
    BowSVM(string dir_path);
    void saveSamples(string samplePath);
    void saveFeature(string descriptorPath);
    void saveCluster(string vocabularyPath);
};

#endif // TRAINSVM_H
