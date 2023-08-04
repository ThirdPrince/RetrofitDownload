## Download files with Retrofit and show progress
 
使用 Kotlin 协程和 Retrofit 展示下载文件并且显示进度

## 介绍

该应用程序使用了以下 Jetpack 组件：

- ViewModel：用于管理 UI 数据，以便在配置更改（例如旋转屏幕）时保留数据。
- LiveData：用于观察 ViewModel 中的数据，并在数据更改时更新 UI。
- flow：加强版本额LiveData。

此外，该应用程序还使用了 Kotlin 协程来管理异步操作，并使用 Retrofit 和 GSON 库从网络下载文件(这个demo 使用图片来展示)。

## 功能

该应用程序有以下功能：

- 显示从网络中请求文件并且显示进度


## 如何运行

要在本地构建和运行该应用程序，请按照以下步骤操作：

1. 克隆此仓库或下载并解压缩 ZIP 文件。
2. 在 Android Studio 中打开项目。
3. 使用 Android Studio 中的 AVD Manager 创建一个模拟器。
4. 点击 "Run" 按钮，选择模拟器并运行应用程序。

## demo

<img src="screenshots/1.gif?raw=true" height="480">
<img src="screenshots/2.gif?raw=true" height="480">

## 作者

该项目由 [ThirdPrince](https://github.com/ThirdPrince) 创建和维护。

## 许可证

该项目使用 MIT 许可证。请参阅 LICENSE 文件以获取更多详细信息。