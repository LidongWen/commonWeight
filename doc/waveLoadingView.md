# WaveLoadingView ____ 水波纹进度View

## 效果展示
<img width="320" height="548" src="https://github.com/LidongWen/commonWeight/blob/master/img/waveLoadingView.gif"></img>



## xml 参数

| name        | type           | direction  |
| ------------- |:-------------:| :-----:|
| circle_Color      | color|reference | 圆颜色 |
| wave_Color      | color|reference      |  水波纹颜色 |
| text_Color | color|reference      |    文字颜色 |
| text_Size | dimension      |    文字大小 |
| unitText | dimension      |    单位（默认 “%”） |
| unitText_Color | color|reference      |    单位文字颜色 |
| unitText_Size | dimension      |     单位文字大小 |


## void
| name        | void name           | direction  |
| ------------- | ------------- | :-----:|
| 设置百分比      | setPercent(int percent) | |
| 设置文字颜色      | setTextColor(int textColor)      |  |
| text size | setTextSize(float textSize)      |    换算成 px |
| 设置符号颜色 | setUnitTextColor(int unitTextColor)      |     |
| 设置符号大小 | setUnitTextSize(float unitTextSize)      |   换算成 px  |
| 设置符号文字 | setUnitText(String unitText)      |   默认“%” |
| 设置圆圈颜色 | setCircleColor(int circleColor)     |      |
| 设置水波颜色 | setWaveColor(int waveColor)     |      |

## 引用
```groovy
// 项目引用
dependencies {
    compile 'com.github.LidongWen:commonWeight:xxx'
}

// 根目录下引用
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.1.0'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'
    }
}

allprojects {
    repositories {
        jcenter()
        maven { url "https://www.jitpack.io" }
    }
}
```

#Contact me

E-mail:wenld2014@163.com

blog: [wenld's blog](http://blog.csdn.net/sinat_15877283)
