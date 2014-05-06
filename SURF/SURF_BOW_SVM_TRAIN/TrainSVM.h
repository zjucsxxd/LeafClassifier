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
    string dir_path;
    string descriptorPath;
    string vocabularyPath;
    string samplePath;
    vector<string> ReadFolderName();
    vector<string> ReadAllImage();
    void UnifyImageSize(cv::Mat& image);

public:
    TrainSVM();
    string FeatureExtractAndCluster();
    void ConstractBoW(Ptr<FeatureDetector> &detector,BOWImgDescriptorExtractor &bowExtractor1, map<string,Mat>& samples);
    void Train(map<string,Mat>& samples,int cols,int type);

};

#endif // TRAINSVM_H
