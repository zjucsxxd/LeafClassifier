#ifndef FILEUTIL_H
#define FILEUTIL_H
#include <opencv2/core/core.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/contrib/contrib.hpp>
#include <vector>
#include<string>
#include<fstream>
#include<iostream>
using namespace std;
using namespace cv;
class FileUtil
{
public:
    FileUtil();
    vector<string> ReadFolderName();
    vector<string> ReadAllImage(string dir_path);
    void UnifyImageSize(cv::Mat& image);
};

#endif // FILEUTIL_H
