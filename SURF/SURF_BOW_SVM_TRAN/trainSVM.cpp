#include "TrainSVM.h"
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
    //以下使用的是one vs all 方法。
    for (int i=0;i<categoryList.size();i++)
    {
        string category=categoryList[i];
        //--step 1：设置正样本标签为1
        Mat allsamples(0,cols,type);
        Mat lables(0,1,CV_32FC1);
        allsamples.push_back(samples[category]);
        Mat posResponses=Mat::ones(samples[category].rows,1,CV_32FC1);
        lables.push_back(posResponses);

        //--step2：设置负样本标签为-1
        map<string,Mat>::iterator iter1;
        for(iter1=samples.begin();iter1!=samples.end();++iter1)
        {
             string not_category=iter1->first;
             if(not_category==category)
                continue;
            allsamples.push_back(samples[not_category]);
            posResponses=-1*Mat::ones(samples[not_category].rows,1,CV_32FC1);
            lables.push_back(posResponses);
        }
        //--step3：设置SVM参数
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

        //--step4：训练SVM分类器并保存
        CvSVM SVM;
        SVM.train(sample_32f,lables,Mat(),Mat(),svmParams);
        string classifier_name("SVM_classifier_");
        classifier_name="picture\\"+classifier_name+category+".yml";
        SVM.save(classifier_name.c_str());
    }
}

