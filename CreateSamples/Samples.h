#ifndef SAMPLES_H
#define SAMPLES_H
#include<iostream>
#include<opencv2/opencv.hpp>
#include<opencv2/contrib/contrib.hpp>
#include<vector>
using namespace std;
using namespace cv;
class Samples{
private:
    void LoadPic(string sourceFile,vector<string>&pics);
public:
    void ImgPrePro(string sourceFile, string saveFile);

};
#endif // SAMPLES_H
