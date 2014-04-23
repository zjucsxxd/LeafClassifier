#ifndef TRAINSVM_H
#define TRAINSVM_H
#include <opencv2/core/core.hpp>
#include <opencv2/ml/ml.hpp>
#include<vector>
#include<string>
#include<map>
using namespace std;
using namespace cv;
class TrainSVM
{
public:
    TrainSVM();
    void Train(map<string,Mat>& samples,int cols,int type);
};

#endif // TRAINSVM_H
