#ifndef MYWIDGET_H
#define MYWIDGET_H

#include <QWidget>
#include<QPushButton>
#include<QLineEdit>
#include<QGraphicsView>
#include<QGraphicsScene>
#include<QGraphicsPixmapItem>
#include<QPixmap>
#include "opencv2/opencv.hpp"
#include "opencv2/core/core.hpp"
#include "opencv2/nonfree/features2d.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/highgui/highgui.hpp"
using namespace std;
using namespace cv;

class MyWidget : public QWidget
{
    Q_OBJECT
private:
    QPushButton *button1;
    QPushButton *button2;
    QPushButton *button3;
    QLineEdit   *text2;
    QGraphicsScene *scene;
    QGraphicsView *view;
    QGraphicsPixmapItem *item;
    Ptr<FeatureDetector > detector;
    Ptr<DescriptorMatcher > matcher;
    Ptr<DescriptorExtractor > extractor;
    Ptr<BOWImgDescriptorExtractor > bowExtractor;
    void UnifyImageSize(cv::Mat& image);
    void OpenPicture();
    float ratioYes=0.0;
    float ratioNo=0.0;
    float ratio=0.0;


public:
    explicit MyWidget(QWidget *parent = 0);
    ~MyWidget();
signals:

public slots:
    void bt1_clicked();
    void bt2_clicked();
    void bt3_clicked();

};

#endif // MYWIDGET_H
