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
#include <vector>
#include <fstream>
#include <iostream>
#include <QPixmap>
#include <QString>


using namespace cv;
using namespace std;

MyWidget::MyWidget(QWidget *parent ) :
    QWidget(parent),extractor(new SurfDescriptorExtractor(400)),
    matcher(new FlannBasedMatcher()),bowExtractor(extractor, matcher)
{
    this->setGeometry(350,100,480,480);
    button1=new QPushButton("显图",this);
    button2=new QPushButton("识别",this);
    button1->setGeometry(0,0,120,50);
    button2->setGeometry(0,50,120,50);
    scene = new QGraphicsScene;
    view = new QGraphicsView(scene,this);
    item = new QGraphicsPixmapItem;
    view->setScene(scene);
    view->setGeometry(120,50,360,400);
    item->setPixmap(QPixmap("picture\\logo.jpg"));
    scene->addItem(item);
    view->show();

    text1=new QLineEdit("输入图片路径",this);
    text1->setGeometry(120,0,360,50);
    text2=new QLineEdit("这是一张...",this);
    text2->setGeometry(120,430,360,50);

    Mat vocabulary;
    FileStorage fs("picture\\vocabulary.yml", FileStorage::READ);
    fs["vocabulary"] >> vocabulary;
    fs.release();
    bowExtractor.setVocabulary(vocabulary);
    connect(button1,SIGNAL(clicked()),this,SLOT(bt1_clicked()));
    connect(button2,SIGNAL(clicked()),this,SLOT(bt2_clicked()));


}
void MyWidget::bt1_clicked()
{
    QString path=text1->text();
    String str=path.toStdString();
    cout<<str<<endl;
    item->setPixmap(QPixmap(path));
    scene->addItem(item);
    view->show();
    text2->setText("");
}
void MyWidget::bt2_clicked()
{

    QString str=text1->text();
    string path=str.toStdString();
    cout<<path<<endl;
    Mat img=imread(path);
    this->UnifyImageSize(img);
    cout<<img.cols<<endl;
    //计算输入图片的bag of words
    vector<KeyPoint> keypoints;
    Mat descriptors;
    Ptr<FeatureDetector> detector(new SurfFeatureDetector(400));
    detector->detect( img, keypoints );
    bowExtractor.compute(img,keypoints,descriptors);
    //读取种类列表
    ifstream fin("picture\\category.list");
    vector <string> folderlist;
    while(!fin.eof())
    {
        string folder;
        fin>>folder;
        folderlist.push_back(folder);
    }

    int isrecong=0;
    for(int i=0;i<folderlist.size();i++)
    {
        string folder=folderlist[i];
        string classifier_name="picture\\SVM_classifier_"+folder+".yml";
        cout<<classifier_name<<endl;
        CvSVM mySVM;
        mySVM.load(classifier_name.c_str());
        float ret1=mySVM.predict(descriptors,true);
        int ret=mySVM.predict(descriptors);
        cout<<i<<"   "<<ret<<" "<<ret1<<endl;
        if(ret==1)
        {
            QString answer=QString::fromStdString(folder);
            text2->setText("这是"+answer+"叶片");
            isrecong=1;
            i=folderlist.size();
        }

    }
    if(isrecong==0)
    {
        text2->setText("不能识别的物体");
    }
}

MyWidget::~MyWidget()
{
    delete button1;
    delete button2;
    delete text1;

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
