#include "BowSVM.h"
#include <iostream>
#include <opencv2/opencv.hpp>
#include <string>
#include <vector>

using namespace std;
using namespace cv;

int main()
{
    string dir_path="..\\resource\\";
    string descriptorPath="..\\resource\\allDescriptors.yml";
    string vocabularyPath="..\\resource\\vocabulary.yml";
    string samplePath="..\\resource\\samples.yml";
    BowSVM svm(dir_path);
    svm.train();
    svm.saveSamples(samplePath);
    svm.saveCluster(vocabularyPath);
    svm.saveFeature(descriptorPath);
    cout<<"game over"<<endl;
    return 0;

}

