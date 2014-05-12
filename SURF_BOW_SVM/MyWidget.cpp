#include "MyWidget.h"
#include<QPushButton>
#include<QLineEdit>
#include<QGraphicsView>
#include<QGraphicsScene>
#include<QGraphicsPixmapItem>
#include <opencv2/ml/ml.hpp>
#include "opencv2/opencv.hpp"
#include "opencv2/core/core.hpp"
#include <opencv2/contrib/contrib.hpp>
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/nonfree/features2d.hpp"
#include "opencv2/features2d/features2d.hpp"
#include <list>
#include <map>
#include<utility>
#include <vector>
#include <fstream>
#include <iostream>
#include <QPixmap>
#include <QString>
#include<QtGui>
#include<QFileDialog>
#include<QMessageBox>
using namespace cv;
using namespace std;
QString filename;

MyWidget::MyWidget(QWidget *parent ) :
    QWidget(parent), detector(new SurfFeatureDetector(400)),
    matcher(new FlannBasedMatcher()),
    extractor(new OpponentColorDescriptorExtractor(Ptr<DescriptorExtractor>(new SurfDescriptorExtractor(400))))
{
    this->setGeometry(350,100,480,480);
    button1=new QPushButton("选择图片",this);
    button2=new QPushButton("识别",this);
    button3=new QPushButton("识别率",this);
    button1->setGeometry(0,0,160,40);
    button2->setGeometry(160,0,160,40);
    button3->setGeometry(320,0,160,40);
    scene = new QGraphicsScene;
    view = new QGraphicsView(scene,this);
    item = new QGraphicsPixmapItem;
    view->setScene(scene);
    view->setGeometry(0,40,480,440);
    item->setPixmap(QPixmap("..\\resource\\logo.jpg"));
    scene->addItem(item);
    view->show();

    Mat vocabulary;
    FileStorage fs("..\\resource\\vocabulary.yml", FileStorage::READ);
    fs["vocabulary"] >> vocabulary;
    fs.release();
    bowExtractor = Ptr<BOWImgDescriptorExtractor>(new BOWImgDescriptorExtractor(extractor,matcher));
    bowExtractor->setVocabulary(vocabulary);

    connect(button1,SIGNAL(clicked()),this,SLOT(bt1_clicked()));
    connect(button2,SIGNAL(clicked()),this,SLOT(bt2_clicked()));
    connect(button3,SIGNAL(clicked()),this,SLOT(bt3_clicked()));

}

void MyWidget::bt1_clicked()
{
    this->OpenPicture();
    item->setPixmap(QPixmap(filename));
    scene->addItem(item);
    view->show();
}
void MyWidget::bt3_clicked()
{
    int count=ratioYes+ratioNo;
    if(count<1)
    {
       QMessageBox::information(this,tr("识别率"),tr("亲，别急~"),QMessageBox::Yes,QMessageBox::Yes);
    }
    else
    {
        ratio=ratioYes/count;
        cout<<ratio<<endl;
        QString Qratio=QString("%1").arg(ratio);
        QMessageBox::information(this,tr("识别结果"),Qratio,QMessageBox::Yes,QMessageBox::Yes);
    }

}
void MyWidget::bt2_clicked()
{
    string path=filename.toStdString();
    cout<<path<<endl;
    Mat img=imread(path);
    this->UnifyImageSize(img);
    //计算输入图片的bag of words
    vector<KeyPoint> keypoints;
    Mat descriptors;
    detector->detect( img, keypoints );
    bowExtractor->compute(img,keypoints,descriptors);
    //读取种类列表
    ifstream fin("..\\resource\\category.list");
    vector <string> folderlist;
    while(!fin.eof())
    {
        string folder;
        fin>>folder;
        folderlist.push_back(folder);
    }
    map<float,string> result;
    for(int i=0;i<folderlist.size();i++)
    {
        string folder=folderlist[i];
        string classifier_name="..\\resource\\SVM_classifier_"+folder+".yml";
        cout<<classifier_name<<endl;
        CvSVM mySVM;
        mySVM.load(classifier_name.c_str());
        float ret1=mySVM.predict(descriptors,true);
        int ret=mySVM.predict(descriptors);
        cout<<i<<"   "<<ret<<" "<<ret1<<endl;
        if(ret==1)
        {
            result.insert(pair<float,string>(ret1,folder));
        }

    }
    if(result.size()==0)
    {
        ratioNo=ratioNo+1;
        QMessageBox::information(this,tr("识别结果"),tr("亲，我不能识别"));
    }
    else
    {

        map<float,string>::iterator iter;
        string answer;
        float max=0;
        for(iter=result.begin();iter!=result.end();iter++)
        {
            float a=-1*iter->first;
            if(a>max)
            {
                max=a;
                answer=iter->second;
            }

        }
        QString Qanswer=QString::fromStdString(answer);
        QMessageBox msgBox;
        msgBox.setWindowTitle("识别结果");
        msgBox.setText(Qanswer);
        msgBox.setStandardButtons(QMessageBox::Yes|QMessageBox::No);
        msgBox.setDefaultButton(QMessageBox::Yes);
        int DO=msgBox.exec();
        switch(DO)
        {
        case QMessageBox::Yes:
            ratioYes=ratioYes+1;
            break;
        case QMessageBox::No:
            ratioNo=ratioNo+1;
            break;
        }
 //       QMessageBox::information(this,tr("识别结果"),Qanswer,QMessageBox::Yes|QMessageBox::No,QMessageBox::Yes);
    }
}

void MyWidget::UnifyImageSize(Mat &image)
{
    Mat unified_image;
    int s = cv::min(image.rows, image.cols);
    float scale = 128.0 / s;
    Size size(image.cols  * scale, image.rows * scale);
    cv::resize(image, unified_image, size);
    cv::medianBlur(unified_image, unified_image, 3);
    image = unified_image;

}
void MyWidget::OpenPicture()
{
    filename = QFileDialog::getOpenFileName(0,
                                            "Open Image",
                                            "../resources/testPicture",
                                            "Image Files (*.png *.jpg *.bmp)");


}
MyWidget::~MyWidget()
{

}
