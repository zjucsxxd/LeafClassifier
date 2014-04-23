#ifndef BAGOFWORD_H
#define BAGOFWORD_H
#include <opencv2/core/core.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/contrib/contrib.hpp>
#include <string>
#include <map>
#include <vector>
#include <iostream>
using namespace std;
using namespace cv;
class BagOfWord
{
public:
    BagOfWord();
    void ConstractBoW(Ptr<FeatureDetector> &detector,BOWImgDescriptorExtractor &bowExtractor, map<string,Mat>& samples);
};

#endif // BAGOFWORD_H
