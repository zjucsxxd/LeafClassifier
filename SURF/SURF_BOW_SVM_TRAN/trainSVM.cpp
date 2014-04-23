#include "TrainSVM.h"
string path="E:\\picture\\";
TrainSVM::TrainSVM()
{
}

void TrainSVM::Train(map<string,Mat>& samples,int cols,int type)
{
    map<string,Mat>::iterator iter;
    vector<string>categoryList;
    for(iter=samples.begin();iter!=samples.end();++iter)
    {
        categoryList.push_back(iter->first);

    }
    //one vs all method
    for (int i=0;i<categoryList.size();i++)
    {
        string category=categoryList[i];
        //copy category samples and lable;
        Mat allsamples(0,cols,type);
        Mat lables(0,1,CV_32FC1);
        allsamples.push_back(samples[category]);
        Mat posResponses=Mat::ones(samples[category].rows,1,CV_32FC1);
        lables.push_back(posResponses);
        //copy the rest samples and lable
        map<string,Mat>::iterator iter1;
        for(iter1=samples.begin();iter1!=samples.end();++iter1)
        {
             string not_category=iter1->first;
             if(not_category==category)
                continue;
            allsamples.push_back(samples[not_category]);
         //   posResponses=Mat::zeros(samples[not_category].rows,1,CV_32FC1);
         //    posResponses=Mat::zeros(samples[not_category].rows,1,CV_32FC1);
             posResponses=-1*Mat::ones(samples[not_category].rows,1,CV_32FC1);
            lables.push_back(posResponses);
        }
        //train and save
        if(allsamples.rows==0)
            continue;
        Mat sample_32f;
        CvSVMParams svmParams;
        allsamples.convertTo(sample_32f,CV_32F);
        svmParams.svm_type=CvSVM::C_SVC;
        svmParams.kernel_type=CvSVM::RBF;
        svmParams.C=5;
        svmParams.gamma=0.1;
        svmParams.term_crit.epsilon=1e-8;
        svmParams.term_crit.max_iter=1e9;
        svmParams.term_crit.type=CV_TERMCRIT_ITER | CV_TERMCRIT_EPS;
//          svmParams.svm_type = CvSVM::C_SVC;
//          svmParams.kernel_type = CvSVM::LINEAR;
//          svmParams.term_crit = cvTermCriteria(CV_TERMCRIT_ITER, 1000, FLT_EPSILON);
//          svmParams.C = 0.01;

        CvSVM SVM;
        SVM.train(sample_32f,lables,Mat(),Mat(),svmParams);
        //save classifier
        string classifier_name("SVM_classifier_");
        classifier_name="E:\\picture\\"+classifier_name+category+".yml";
        SVM.save(classifier_name.c_str());

    }
}

