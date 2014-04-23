#ifndef FEATURE_H
#define FEATURE_H
#include <FileProcess.h>
#include <opencv2/core/core.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <vector>
#include<string>
using namespace std;
using namespace cv;
class Feature
{
public:
    Feature();
    void FeatureExtractAndCluster(string dir_path,string descriptorPath,string vocabularyPath);
};

#endif // FEATURE_H
