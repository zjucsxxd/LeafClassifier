#ifndef FILEPROCESS_H
#define FILEPROCESS_H
#include <opencv2/core/core.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/contrib/contrib.hpp>
#include <vector>
#include<string>
#include<fstream>
using namespace std;
using namespace cv;


class FileProcess
{
public:
    FileProcess();
    vector<string> ReadFolderName();
    vector<string> ReadAllImage(string dir_path);
    void UnifyImageSize(cv::Mat& image);

};

#endif // FILEPROCESS_H
