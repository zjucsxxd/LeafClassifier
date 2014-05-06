TEMPLATE = app
CONFIG += console
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += main.cpp \
    TrainSVM.cpp

INCLUDEPATH+=E:\opencv\include
LIBS+=-LE:\opencv\lib -lopencv_core248 -lopencv_highgui248 -lopencv_imgproc248 -lopencv_features2d248 -lopencv_nonfree248 -lopencv_flann248 -lopencv_ml248 -lopencv_contrib248

HEADERS += \
    TrainSVM.h
