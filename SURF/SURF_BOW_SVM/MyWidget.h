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
    QLineEdit   *text1;
    QLineEdit   *text2;
    QGraphicsScene *scene;
    QGraphicsView *view;
    QGraphicsPixmapItem *item;
    Ptr<DescriptorExtractor> extractor;
    Ptr<DescriptorMatcher> matcher;
    BOWImgDescriptorExtractor bowExtractor;


public:
    explicit MyWidget(QWidget *parent = 0);
    void UnifyImageSize(cv::Mat& image);
    ~MyWidget();
signals:

public slots:
    void bt1_clicked();
    void bt2_clicked();

};

#endif // MYWIDGET_H
