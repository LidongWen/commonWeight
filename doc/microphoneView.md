# 加头加尾RecyclerView 适配器

<img width="320" height="548" src="https://github.com/LidongWen/commonWeight/blob/master/img/microphoneView.gif"></img>

#### 使用

        <attr name="ringWidth" format="reference|dimension" />
        <attr name="voiceWidth" format="dimension|reference" />
        <attr name="microphoneBitmap" format="reference" />
        <attr name="model">
            <enum name="record_model" value="1" />
            <enum name="play_model" value="2" />
            <enum name="loading_model" value="3" />
        </attr>

        <attr name="ringColor" format="reference|color" />
        <attr name="loadingColor" format="reference|color" />
        <attr name="voiceRingColor" format="reference|color" />
        <attr name="voiceColor" format="reference|color" />
## xml
| name        | type           | direction  |
| ------------- |:-------------:| :-----:|
| playHintText      | string | 标识中的文字 |
| textHintColor      | color / reference      |  标识文字的颜色 |
| hintTextSize | reference / dimension    |    文字大小 |
| ringWidth | reference / dimension      |    外圈圆环粗细 |
| voiceWidth | reference / dimension      |    录音波纹粗细 |
| microphoneBitmap | reference  |    麦克风图标 |
| model | enum      |     模式：record_model：录音模式  play_model：播放模式  loading_model：加载模式|
| ringColor | color / reference    |    外圈圆环颜色 |
| loadingColor | color / reference     |    加载条颜色 |
| voiceRingColor | color / reference  |    录音圆环颜色 |
| voiceColor | color / reference  |    录音圆环颜色 |

## void
相对应以上有 java方法

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
