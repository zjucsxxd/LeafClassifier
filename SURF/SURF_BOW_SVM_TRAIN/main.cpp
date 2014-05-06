#include <TrainSVM.h>
#include <opencv2/core/core.hpp>
#include <opencv2/nonfree/features2d.hpp>
#include <opencv2/contrib/contrib.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <string>
#include <iostream>
#include <iomanip>
#include <sstream>
#include<fstream>
#include <map>
using namespace std;
using namespace cv;

int main()
{

   //--step 1:提取图片特征并聚类
   TrainSVM trainSVM;
   string vocabularyPath=trainSVM.FeatureExtractAndCluster();

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
   map<string, Mat> samples;
   trainSVM.ConstractBoW(detector,bowExtractor,samples);

   //--step 3:训练 SVM
   trainSVM.Train(samples,bowExtractor.descriptorSize(),bowExtractor.descriptorType());
   cout<<"~ hah , game over "<<endl;
   return 0;
}

