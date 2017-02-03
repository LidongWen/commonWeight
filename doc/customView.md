# 自定义View基类
自定义View基类，帮助你节省部分代码

## 通常的自定义View类
需要自己再去计算那些基础值：中心点 画布大小等；

需要手动重载 测量、sizeChange这些方法；

```java
public class MyView extends View {
 //画布大小
    protected int mWidth;
    protected int mHeight;


    //中心点 坐标
    protected int mCenterX;
    protected int mCenterY;

    public MyView(Context context) {
        super(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        initValue();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = measure(widthMeasureSpec);
        mHeight = measure(heightMeasureSpec);
        mCenterX = (mWidth - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
        mCenterY = (mHeight - getPaddingTop() - getPaddingBottom()) / 2 + getPaddingTop();

        circleR = Math.min(mWidth - getPaddingLeft() - getPaddingRight(), mHeight - getPaddingTop() - getPaddingBottom()) / 2;
        setMeasuredDimension(mWidth, mHeight);
    }

    private int measure(int origin) {
        int result = dip2px(DEFAULT_MIN_WIDTH);
        int specMode = MeasureSpec.getMode(origin);
        int specSize = MeasureSpec.getSize(origin);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        reset();
    }
}
```

## 继承 CustomView
直接计算好基础值；

抽象实现方法；

```java
public class MyView extends CustomView {
  public MIUITimeView(Context context) {
        super(context);
    }

    public MIUITimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

/**
* 初始化 自定义属性
*/
    @Override
    public void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
        }
    }
/**
* 初始化值
*/
    @Override
    public void initValue() {
    }
 /**
 * 初始化bound
*/
    public void reset() {
    }

}
```

### 属性

```java
//画布大小
protected int mWidth;
protected int mHeight;

//View默认最小宽度
protected static final int DEFAULT_MIN_WIDTH = 200;

//中心点 坐标
protected int mCenterX;
protected int mCenterY;

//园半径，如非园型 请忽略此属性
protected float circleR;

```

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
