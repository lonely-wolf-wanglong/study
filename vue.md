# Vue

## 前端工程化

- 是指在企业级的前端项目开发中，把前端开发所需要的工具，技术，流程，经验等进行规范化，标准化。
- 解决方案：grunt, gulp, webpack, parcel,Vite

### Webpack

- webpack 是前端项目工程化的具体解决方案
- 主要功能：它提供了友好的前端模块化开发支持，以及代码压缩混淆，处理浏览器端 JavaScript 的兼容性，性能优化等强大的功能。
- 示例介绍

```JS
    //1.新建项目空白目录，并运行npm init -y命令，初始化包管理配置文件webpack.json
    //2.新建src源代码目录
    //3.新建src->index.html首页和src->index.js脚本文件
    //4.初始化首页基本结构
    //index.html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <script src="./index.js"></script>
    </head>
    <body>
        <ul>
            <li>这是第1个li</li>
            <li>这是第2个li</li>
            <li>这是第3个li</li>
            <li>这是第4个li</li>
            <li>这是第5个li</li>
            <li>这是第6个li</li>
            <li>这是第7个li</li>
            <li>这是第8个li</li>
            <li>这是第9个li</li>
        </ul>
    </body>
    </html>
    //5.运行npm install jquery -S命令，安装jQuery
    //6.通过ES6模块化的方式导入jQuery,实现列表隔行变色效果
    //index.js
    //1. 使用ES6导入语法，导入jQuery
    import $ from 'jquery'


    //2.定义jQuery的入口函数
    $(function(){
        $('li:odd').css('background-color', 'red');
        $('li:even').css('backgroud-color', 'pink');
    })
```

- 安装配置 webpack
  - 安装过程
  ```webpack
      npm install webpack@5.42.1 webpack-cli@4.7.2 -D
      -D 是 --save-dev
      -S 是 --save
  ```
  - 在项目根目录中创建名为 webpack.config.js 的 webpack 配置文件，并初始化基本配置。
  ```webpack
      module.exports={
          mode: 'development' //mode 用来指定构建模式，可选值有development和production
      }
  ```
  - 在 package.json 的 scripts 结点下，新增 dev 脚本如下:
  ```webpack
      "script": {
          "dev": "webpack"    // script节点下的脚本,可以通过npm run执行， 例如 npm run dev
      }
  ```
  - 在终端运行 npm run dev 命令，启动 webpack 进行项目的打包构建。
- 在 webpack4.x 和 5.x 的版本中，有如下默认的约定
  - 默认的打包入口文件为 src->index.js
  - 默认的输出文件路径为 dist->main.js
- 在 webpack.config.js 配置文件中，可以通过 entry 节点指定打包的入口，通过 output 节点指定打包的出口

```webpack
    const path = require('path')

    module.exports={
        mode: 'development', //mode 用来指定构建模式，可选值有development和production

        //entry: 指定要处理哪个文件
        entry: path.join(__dirname, './src/index.js'),
        output: {
            path: path.join(__dirname,'./dist'),    //输出文件的存放路径
            __filename: 'main.js'   //输出文件的名称
        }
    }
```

- webpack 插件的作用

  - 通过安装和配置第三方的插件，可以拓展 webpack 的能力，从而让 webpack 用起来更方便。最常用的 webpack 插件有如下俩个：
  - webpack-dev-server:
    - 类似于 node.js 阶段用到的 nodemon 工具
    - 每当修改了源代码，webpack 会自动进行项目的打包和构建
  - html-webpack-plugin
    - webpack 中的 HTML 插件(类似一个模板引擎插件)
    - 可以通过此插件定制 index.html 页面的内容
  - 安装 webpack-dev-server
    - npm install webpack-dev-server@3.11.2 -D
  - 配置 webpack-dev-server
    - 修改 package.json->scripts 中的 dev 命令
    ```webpack
        "scripts":{
            "dev": "webpack serve"
        }
    ```
    - 再次运行 npm run dev 命令，重新进行项目的打包
    - 在浏览器中访问，查看自动打包效果。
  - 安装 html-webpack-plugin:运行 npm install html-webpack-plugin@5.3.2 -D
  - 配置 html-webpack-plugin

  ```webpack
      //1.导入HTML插件，得到一个构造函数
      const HtmlPlugin = require('html-webpack-plugin')
      //2.创建HTML插件的实例对象
      const htmlPlugin = new HtmlPlugin({
          template: './src/index.html',   //指定原文件的路径
          filename: './index.html' //指定生成的文件的存放路径
      })

      module.exports = {
          mode: 'development',
          plugins: [htmlPlugin] //3.通过plugins节点，使htmlPlugin插件生效
      }
  ```

- 在 webpack.config.js 配置文件中，可以通过 devServer 节点对 webpack-dev-server 插件进行更多的配置

```webpack
    devServer: {
        open: true, //初次打包完成后，自动打开浏览器
        host: '127.0.0.1',  //实时打包所使用的主机地址
        port: 80    //实时打包所使用的端口号
    }
```

- loader 概述
  - 在实际开发过程中，webpack 默认只能打包处理以.js 后缀名结尾的模块。其他非.js 后缀名结尾的模块，webpack 默认处理不了，需要调用 loader 加载器才可以正常打包，否则会报错。
  - loader 加载器的作用:
  - css-loader:可以打包处理.css 相关的文件
  - less-loader:可以打包处理.less 相关的文件
  - babel-loader:可以打包处理 webpack 无法处理的高级 js 语法
- 打包处理 css 文件
  - 运行 npm i style-loader@3.0.0 css-loader@5.2.6 -D 命令，安装处理 css 文件的 loader
  - 在 webpack.config.js 的 module->rules 数组中，添加 loader 规则如下:
  ```webpack
      module: {
          rules[
              { test:/\.css$/, use['style-loader', 'css-loader']}
          ]
      }
  ```
  - 其中 test 表示匹配的文件类型，use 表示调用对应的 loader
  - use 数组中指定的 loder 顺序是固定的
  - 多个 loader 的调用顺序是从后往前调用
- 打包处理 less 文件
  - 运行 npm i less-loader@10.0.1 less@4.1.1 -D 命令
  - 在 webpack.config.js 的 module-rules 数组中，添加 loader 规则
  ```webpack
      module: {
          rules[
              { test:/\.less$/, use:['style-loader', 'css-loader','less-loader']}
          ]
      }
  ```
- 打包处理样式表中与 url 路径相关的文件
  - 运行 npm i url-loader@4.1.1 file-loader@6.2.0 -D 命令
  - 在 webpack.config.js 的 module->rules 数组中，添加 loader 规则
  ```webpack
      modules: {
          rules[
              {test:/\.jpg|pgn|gif$/, use: 'url-loader?limit=22229' }
          ]
      }
  ```
  - ?之后的是 loader 的参数项:limit 用来指定图片大小，单位是字节。
  - 只有小于 limit 大小的图片才会被转为 base64 格式的图片
- 打包处理 js 文件中的高级语法

  - webpack 只能打包处理一部分高级的 JavaScript 语法。对于那些 webpack 无法处理的高级 js 语法，需要借助于 babel-loader 进行打包处理。
  - 安装 babel-loader 相关的包:npm i babel-loader@8.2.2 @babel/core@7.14.6 @babel/plugin-proposal-decorators@7.14.5 -D
  - 在 webpack.config.js 的 module->rules 数组中，添加 loader 规则如下:

  ```webpack
      { test: /\.js$/, use: 'babel-loader', exclude: /node_modules/}

      module.exports = {
          plugins: [['@babel/plugin-proposal-decorators', {legacy: true} ]]
      }
  ```

  - 在项目根目录下，创建名为 babel.config.js 的配置文件，定义 Babel 的配置如上

- 配置 webpack 的打包发布

  - 在 package.json 文件的 scripts 节点，新增 build 命令:

  ```webpack
      "scripts": {
          "dev": "webpack serve",
          "build": "webpack --mode production"
      }
  ```

  - 通过--model 指定的参数项，会覆盖 webpack.config.js 中的 model 选项。

- 把 Javascript 文件统一生成到 js 目录中
  - 在 webpack.config.js 配置文件的 output 节点中，进行配置
  ```webpack
      output: {
          path: path.join(__dirname, 'dist'),
          filename: 'js/main.js'
      }
  ```
- 把图片文件统一生成到 image 目录中
  - 修改 webpack.config.js 中的 url-loader 配置项，新增 outputPath 选项即可指定图片文件的输出路径
  ```webpack
     module: {
          rules: [
              { test: /\.jpg|pgn|gif$/, use: 'url-loader?limit=22229&outputPath=images' }
          ]
      }
  ```
- 配置发布 clean-webpack-plugin 自动删除 dist 文件

  - 安装:npm install --save-dev clean-webpack-plugin

  ```webpack
      const { CleanWebpackPlugin } = require('clean-webpack-plugin');

      const webpackConfig = {
          plugins: [
              /**
              * All files inside webpack's output.path directory will be removed once, but the
              * directory itself will not be. If using webpack 4+'s default configuration,
              * everything under <PROJECT_DIR>/dist/ will be removed.
              * Use cleanOnceBeforeBuildPatterns to override this behavior.
              *
              * During rebuilds, all webpack assets that are not used anymore
              * will be removed automatically.
              *
              * See `Options and Defaults` for information
              */
              new CleanWebpackPlugin(),
          ],
      };
  ```

- Source Map
  - Source Map 就是一个信息文件，里面存储着位置信息。就是说 Source Map 文件中存储着压缩混淆后的代码，所对应的转换前的位置
  - 有了它，出错的时候，出错工具将直接显示原始代码，而不是转换后的代码，能够极大方便后期的调试。
  - 在开发环境下默认生成的 Source Map，记录的是生成后的代码位置，会导致运行时报错的行数与源代码的行数不一致的问题。
  - 在开发环境下，推荐在 webpack.config.js 中添加如下配置，即可保证运行时报错行数与源代码的行数保持一致。
  - 在生产环境中，如果只想定位报错的具体行数，且不想暴露源码，此时可以将 devtool 的值设置为 nosources-source-map.
  ```webpack
      module.exports = {
          devtool: 'eval-source-map'
      }
  ```
  - 文件地址重命名
  ```webpack
      resolve: {
          alias: {
              //告诉webpack，程序员写的代码中，@表示src这层目录
              '@' : path.join(__dirname, './scr/')
          }
      }
  ```

## Vue2 的使用

### Vue 简介

- Vue 是一套用于构建用户界面的前端框架
- Vue 的特点：数据驱动视图，双向数据绑定
  - 在使用 Vue 的页面中，vue 会监听数据的变化，从而自动重新渲染页面的结构。当页面数据发生变化时，页面会自动重新渲染。数据驱动视图是单向数据绑定
  - 在填写表单时，双向数据绑定可以辅助开发者在不操作 DOM 的前提下，自动把用户填写的内容同步到数据源。开发者不再需要手动操作 DOM 元素，来获取表单最新的值。
- MVVM:是 vue 实现数据驱动视图和双向数据绑定的核心原理。
  - Model:表示当前也买你渲染时所依赖的数据源
  - View: 表示当前页面所渲染的 DOM 结构
  - ViewModel: 表示 vue 的实例，他是 MVVM 的核心
  - ViewModel 作为 MVVM 的核心，是它把当前页面的数据源和页面的结构连接在一起
  - 当数据源发生变化时，会被 ViewModel 监听到，VM 会根据最新的数据源自动更新页面的结构
  - 当表单元素的值发生变化时，也会被 VM 监听到，VM 会把变化过后最新的值自动同步到 Model 数据源中

### Vue 的使用

- 示例代码

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Document</title>
  </head>
  <body>
    <div id="app">{{ msg }}</div>
    <script src="./lib/vue.js"></script>
    <script>
      const vm = new Vue({
        el: "#app",
        data: {
          msg: "Hello Vue",
        },
      });
    </script>
  </body>
</html>
```

### vue 指令

- 是 vue 为开发者提供的模板语法，用于辅助开发者渲染页面的基本结构
- 指令按照不同的用途可以分为 6 大类
  - 内容渲染指令：用来辅助开发者渲染 DOM 元素的文本内容,v-text,v-html,{{}},
    - v-text 指令会覆盖原有的内容
    - v-html 会解析 html
    - {{}}是插值表达式
  - 属性绑定指令: v-bind,可以简写为:，在使用 v-bind 属性绑定期间，如果绑定内容需要进行动态拼接则字符串的外面应该包裹单引号。
  - 事件绑定指令
  - 双向绑定指令
  - 条件渲染指令
  - 列表渲染指令
- 在 vue 提供的模板渲染语法中，除了支持绑定简单的数据值之外，还支持 Javascript 表达式的运算。
- vue 提供了 v-on 事件绑定指令，用来辅助程序员为 DOM 元素绑定事件监听
- 在事件处理函数中调用 event.preventDefault()或者 event.stopPropagation()是非常常见的需求。因此，vue 提供了事件修饰符的概念
  来辅助程序员更方便的对事件的触发进行控制 - .prevent:阻止默认行为 - .stop:阻止事件冒泡 - .capture:以捕获模式触发当前的事件处理函数 - .once:绑定的事件只触发 1 次 - .self:只有在 event.target 是当前元素自身时触发事件处理函数 - 按键修饰符 - 在监听键盘事件时，我们经常需要判断详细的按键，此时，可以为键盘相关的事件添加按键修饰符。
  `html
    <input @keyup.enter="submit">
    <input @keyup.esc="clearInput">
`
- 双向数据绑定指令

  - vue 提供了 v-model 双向数据绑定指令,用来辅助开发者在不操作 DOM 的前提下，快速获取表单数据

  ```html
  <p>用户名是: {{ username }}</p>
  <input type="text" v-model="username" />

  <p>选中的省份是: {{ province }}</p>
  <select v-model="province">
    <option value="">请选择</option>
    <option value="1">北京</option>
    <option value="2">河北</option>
    <option value="3">黑龙江</option>
  </select>
  ```

- v-model 指令的修饰符

  - 为了方便对用户输入的内容进行处理，vue 为 v-model 指令提供了 3 个修饰符
  - .number:自动将用户的输入值转为数值类型
  - .trim: 自动过滤用户输入的首尾空白字符
  - .lazy: 在 change 时而非 input 时更新

  ```html
  <input type="text" v-model.number="n1" />
  <input type="text" v-model.number="n2" />
  <span>{{ n1+n2 }}</span>
  ```
  - 组件上使用v-model指令
  ```
  <CustomInput v-model="searchText" />
    // 在组件上使用v-model指令，会被解析为如下所示的代码
    <CustomInput
    :modelValue="searchText"
    @update:modelValue="newValue => searchText = newValue"
  />


  //子组件需要实现的代码
  <!-- CustomInput.vue -->
  <script>
    export default {
      props: ['modelValue'],
      emits: ['update:modelValue']
    }
  </script>

  <template>
    <input
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
    />
  </template>

  // 更改绑定的名称
  <MyComponent v-model:title="bookTitle" />

  ```
- v-for 指令用于进行列表循环
  - v-for 指令建议增加 key 属性的使用，其注意事项如下
    - key 的值只能是字符串或者数字类型
    - key 的值必须具有唯一性
    - 建议将数据项 id 属性的值作为 key 的值（因为 id 属性的值具有唯一性）
    - 使用 index 的值当作 key 的值没有任何意义（因为 index 的值不具有唯一性）
    - 建议使用 v-for 指令时一定要指定 key 的值(既提升性能，又防止列表状态紊乱)
  - Vue默认按照就地更新的策略来更新通过v-for渲染的列表。当数据项的顺序发生改变时，Vue不会随之移动DOM元素的顺序，而是就地更新每一个元素，确保他们在原本指定的索引位置上渲染。默认是高效的，但是只适用于列表渲染输出的结果不依赖于子组件的状态或者临时组件的状态。
  - 为了给 Vue 一个提示，以便它可以跟踪每个节点的标识，从而重用和重新排序现有的元素，你需要为每个元素对应的块提供一个唯一的 key

- v-model:该指令用于表单数据的双向绑定
  - .lazy: 改为在每次 change 事件后更新数据
  - .number: 将用户输入直接改为数字
  - .trim: 去除输入数据两端的空格
### Vue 过滤器

- 过滤器是 vue 为开发者提供的功能，常用于文本的格式化。过滤器可以用在两个地方，插值表达式和 v-bind 属性的绑定
- 过滤器应该添加在 Javascript 表达式的尾部，由管道符进行调用,过滤器一定要有返回值。

```html
<p>{{ message | capitalize }}</p>
<div v-bind:id="rawId | formatId"></div>
```

- 在 filters 节点下定义的过滤器称为私有过滤器，因为它只能在当前 vm 实例所控制的 el 区域中使用，如果希望在多个 vue 实例之间共享过滤器，
  则可以按照下面的格式定义全局过滤器

```html
Vue.filter('capitalize',(str)=>{ return str.charAt(0)+str.slice(1); })
```

- 如果全局过滤器和私有过滤器名字一致，此时按照就近原则，调用的是私有过滤器
- 可以串联调用多个过滤器
- 过滤器本质是 JavaScript 函数，因此可以接收参数
- 过滤器仅在 vue2 和 vue1 中受到支持，在 vue3 的版本中剔除了过滤器相关功能。

### 侦听器

- watch 侦听器允许开发者监视数据的变化，从而针对数据的变化做特定的操作
- 默认情况下，用户创建的侦听器回调，都会在 Vue 组件更新之前被调用。这意味着你在侦听器回调中访问的 DOM 将是被 Vue 更新之前的状态
- 如果想在侦听器回调中能访问被 Vue 更新之后的 DOM，你需要指明 flush: 'post' 选项
- 可以通过this.$watch函数来创建监听函数
```
  export default {
    // ...
    watch: {
      key: {
        handler() {},
        flush: 'post'
      }
    }
  }
```
```js
const vm = new Vue({
  el: "#app",
  data: {
    username: "",
    info: {
      phone: "",
    },
  },
  watch: {
    //侦听器的方法格式
    //无法在刚进入页面的时候，自动触发
    //如果侦听的是对象，如果对象中的属性发生变化，不会触发侦听器
    username(newVal, oldVal) {
      console.log(newVal, oldVal);
    },
    // 侦听器的处理函数
    username: {
      handler(newValue, oldValue) {
        console.log(newValue, oldValue);
      },
      //immediate选项的默认值是false
      //immediate的作用是: 让侦听器进入页面时自动触发一次
      immediate: true,
    },
    info: {
      handler(newValue, oldValue) {
        console.log(newValue, oldValue);
      },
      immediate: true,
      //开启深度监听,只要对象中任何一个属性变化，都会触发对象的监听器
      deep: true,
    },
  },
});
```
- watch侦听器在组件卸载之前停止监听,可以调用watch返回函数
```
  const unwatch = this.$watch('foo', callback)
  // ...当该侦听器不再需要时
  unwatch()
```
### 什么是计算属性

- 计算属性是通过一系列运算之后，最终得到一个属性值
- 这个动态计算出来的属性值可以被模板结构或者 methods 方法使用

```js
var vm = new Vue({
  data: {
    r: 0,
    g: 0,
    b: 0,
  },
  computed: {
    rgb() {
      return `rgb(${this.r}, ${this.g}, ${this.b})`;
    },
  },
  methods: {
    show() {
      console.log(this.rgb);
    },
  },
});
```

- 计算属性和方法之间的区别：计算属性具有缓存的效果而方法不具有。具体应该用哪个，要根据实际情况进行来决定。
  - computed 定义的属性在第一次访问时进行计算，以后访问就不再计算，而直接返回上次计算的结果。但是如果计算属性的值依赖于响应式数据，当响应式数据变化的时候，也会重新计算。
  - 定义在 methods 属性中的方法每次都会调用计算。

### axios

- 是一个专注于网络请求的库，https://www.axios-http.cn/
- axios 的基本语法

```js
axios({
    method: '请求的类型',
    url: '请求的url',
}).then(result)=>{
    //then 用来指定请求成功之后的回调函数
    // 形参中的 result是请求成功之后的结果

    ]}
```

## vue-cli

### 单页面应用程序

- 指的是一个 web 网站中只有唯一一个 HTML 页面，所有的功能与交互都在这个唯一的页面内完成
- vue-cli 是 vue.js 开发的标准工具，它简化了程序员基于 webpack 创建工程化的 vue 项目的过程
- vue-cli 是 npm 上的一个全局包，使用 npm install 命令，即可方便的把它安装到自己的电脑上：npm install -g @vue/cli
- 基于 vue-cli 快速生成工程化的 Vue 项目: vue create 项目的名称

### 项目应用文件目录

- assets 文件夹: 存放项目中用到的静态文件资源，如 css 样式表，图片资源
- components 文件夹: 程序员封装的可复用的组件，都要放在 components 文件夹下
- main.js：项目的入口文件，整个项目运行，先执行 main.js
- App.vue: 项目的根组件

### 项目的运行流程

- 在工程化的项目中，vue 要做的事情很单纯：通过 main.js 把 App.vue 渲染到 index.html 的指定区域
- App.vue:用来编写待渲染的模板结构
- index.html: 需要预留一个 el 的区域
- main.js 把 App.vue 渲染到 index.html 所预留的区域

### 组件化开发

- 指的是：根据封装的思想，把页面上可以重用的 UI 结构封装为组件，从而方便项目的开发和维护。
- vue 是一个支持组件化开发的前端框架。vue 中规定:组件的后缀名是.vue，之前接触到的 App.vue 文件本质上就是一个 vue 的组件
- 每一个 vue 组件都是由三部分组成
  - template：组件的模板结构
  - script: 组件的 Javascript 行为
  - style: 组件的样式
- 组件的全局注册：我们可以使用 Vue 应用实例的 app.component() 方法，让组件在当前 Vue 应用中全局可用
- 局部组件注册的优点: 局部注册的组件需要在使用它的父组件中显式导入，并且只能在该父组件中使用。它的优点是使组件之间的依赖关系更加明确，并且对 tree-shaking 更加友好
- 使用组件的三个步骤:
  - 使用 import 语法导入需要的组件
  ```js
  import Left from "@/components/Left.vue";
  ```
  - 使用 components 节点注册组件
  ```js
  export default {
    components: {
      Left,
    },
  };
  ```
  - 以标签形式使用刚才注册的组件
  ```js
  <div class="box">
    <Left></Left>
  </div>
  ```
- 通过 components 注册的是私有子组件
- 在 vue 项目中注册全局组件
  - 在 vue 项目的 main.js 入口文件中，通过 Vue.component()方法，可以注册全局组件。
  ```js
  import Count from "@/components/Count.vue";
  //参数1: 字符串格式，表示组件的注册名称
  //参数2： 需要被全局注册的组件
  Vue.components("MyCount", Count);
  ```
- 组件的 props
  - props 是组件的自定义属性，在封装通用组件的时候，合理地使用 props 可以极大的提高组件的复用性
  ```js
  export default {
      props: ['自定义属性A', '自定义属性B'，'自定义属性C']，
      data(){
          return { }
      }
  }
  ```
  - props 中的数据，可以直接在插值表达式中使用，props 是只读的，不能直接修改 props 的值，否则终端将会报错
  - 子组件要想修改 props 的值，可以把 props 的值转存到 data 中，因为 data 中的数据都是可读可写的。或者可以通过计算属性对父组件传过来的值进行相应的转换。
  - props 的 default 默认值
    - 在声明自定义属性时，可以通过 default 来定义属性的默认值
    ```js
        props: {
            init: {
                //用default属性定义属性的默认值
                default: 0
            }
        }
    ```
  - 在声明自定义属性时，可以通过 type 来定义属性的值类型.
  ```vue
  export default { props: { init: { default: 0 // 使用type属性定义属性值类型 //
  如果传递过来的值不符合此类型,则终端会报错 type: Number } } }
  ```
  - required: 该属性设置必填
  - 使用一个对象绑定多个 prop:
  ```
    export default {
      data() {
        return {
          post: {
            id: 1,
            title: 'My Journey with Vue'
          }
        }
      }
    }

    //下方这两种方式的属性绑定等价
    <BlogPost v-bind="post" />
    <BlogPost :id="post.id" :title="post.title" />
  ```

### 生命周期

- 生命周期是指一个组件从创建，运行到销毁的整个阶段，强调的是一个时间段
- 生命周期函数，是由 vue 框架提供的内置函数，会伴随着组件的生命周期，自动按照次序执行
- 生命周期强调的是时间段，生命周期函数强调的时间点
  ![生命周期](./1.png)

### 组件之间的数据共享

- 在项目开发中，组件之间的最常见的关系分为两种:父子关系和兄弟关系
- 父组件向子组件共享数据

  - 父组件向子组件共享数据需要使用自定义属性

  ```vue
  //父组件
  <Son :msg="message" :user="userinfo"></Son>
  data(){ return { message: 'hello vue.js', userinfo: {name: 'zs', age: 20} } }
  //子组件
  <template>
    <div>
      <h5>Son 组件</h5>
      <p>父组件传递过来的 msg 的值是: {{ msg }}</p>
      <p>父组件传递过来的 user 的值是: {{ user }}</p>
    </div>
    <template />
  </template>
  ```

- 子组件向父组件共享数据

  - 子组件向父组件共享数据使用自定义事件:

  ```vue
  // 子组件 
  export default { 
    data(){ return { count: 0 } }, 
    methods:{ add() {
      this.count+=1 //修改时，通过$emit()触发自定义事件
      this.$emit('numchange',this.count) 
      } 
    } 
  } //父组件
  <Son @numchange="getNewCount"></Son>
 
  export default { data() { return { countFromSon: 0} }, methods: {
  getNewCount(val){ this.countFromSon = val } } }
  ```
 - 这个 emits 选项还支持对象语法，它允许我们对触发事件的参数进行验证
 ```
  export default {
    emits: {
      // 没有校验
      click: null,

      // 校验 submit 事件
      submit: ({ email, password }) => {
        if (email && password) {
          return true
        } else {
          console.warn('Invalid submit event payload!')
          return false
        }
      }
    },
    methods: {
      submitForm(email, password) {
        this.$emit('submit', { email, password })
      }
    }
  }
 ```
- 兄弟之间的数据传递，使用 EventBus
  - 在 Vue2.x 中，兄弟组件之间数据共享的方案是 EventBus
  - 创建 eventBus.js 模块，并向外共享一个 Vue 的实例对象
  - 在数据发送方，调用 bus.$emit('事件名称',要发送的数据)方法触发自定义事件
  - 在数据的接收方,调用 bus.$on('事件名称', 事件处理函数)方法注册一个自定义事件
  ```vue
  //兄弟组件A(数据发送方) import bus from './eventBus.js' export default {
  data() { return { msg: 'hello vue.js' } }, methods: { sendMsg() {
  bus.$emit('share', this.msg) } } } //eventBus.js import Vue from 'vue' export
  default new Vue() //兄弟组件C(数据接收方) import bus from './eventBus.js'
  export default { data(){ return { msgFromLeft: '' } }, }
  ```

### ref 引用

- ref 引用用来辅助开发者在不依赖 jQuery 的情况下，，获取 DOM 元素或组件的引用
- 每一个 vue 的组件上，都包含着一个$refs对象，里面存储着对应的DOM元素或者组件的引用，默认情况下，组件的$refs 指向一个空对象
- 如果想要使用 ref 引用页面上的组件实例，可以按照如下方式进行:

```vue
<!-- 使用ref属性，为对应组件添加引用名称-->
<my-counter ref="counterRef"></my-counter>
<button @click="getRef">获取$refs引用</button>

methods:{ getRef(){ console.log(this.$refs.counterRef)
this.$refs.counterRef.add() } }
```

- 组件的$nextTick(cb)方法，会把 cb 回调推迟到下一个 DOM 更新周期之后执行。通俗的理解是:等组件的 DOM 更新完成之后，再执行 cb 回调函数。从而能保证 cb 回调函数可以操作到最新的 DOM 元素

### 动态组件

- Vue 提供了一个内置的 component 组件，专门用来实现动态组件的渲染,示例代码如下:

```vue
//当前渲染组件的名称 data(){ return {comName: 'Left'} }
//通过is属性，动态指定要渲染的组件
<keep-alive include="Left">
        <component :is="comName"></component>
    </keep-alive>
//点击按钮，动态切换组件名称
<button @click="comName = 'Left'">展示Left组件</button>
<button @click="comName = 'Right'">展示Right组件</button>
```

- keep-alive 属性用于缓存组件
  - 当组件被缓存时会自动触发组件的 deactived 生命周期函数
  - 当组件被激活时，会自动触发组件的 actived 生命周期函数
  - keep-alive 的 include 属性用来指定只有名称匹配的组件会被缓存，多个组件名之间使用英文的逗号分隔。exclude 属性指定谁不会被缓存

### 插槽

- 插槽 slot 是 vue 为组件的封装者提供的能力，允许开发者在封装组件时，把不确定的，希望由用户指定的部分定义为插槽。
- vue 官方规定，每一个 slot 插槽，都要有一个 name 名称，如果省略了 slot 的 name 属性，则有一个默认名称叫做 default
- 默认情况下,在使用组件时，提供的内容都会被填充到名字为 default 的插槽中
- 如果要把内容填充到指定名称的插槽中，需要使用 v-slot 这个指令
- v-slot 后面要跟上插槽的名称
- v-slot 不能直接用在元素身上，必须使用在 template 标签上
- template 这个标签，它是一个虚拟的标签，只能起到包裹性质的作用，但是不会被渲染为任何实质性的 html 元素。v-slot 的简写形式为#
- 插槽包括具名插槽和作用域插槽
- 插槽的使用示例：
```
<div class="container">
  <header>
    <!-- 标题内容放这里 -->
  </header>
  <main>
    <!-- 主要内容放这里 -->
  </main>
  <footer>
    <!-- 底部内容放这里 -->
  </footer>
</div>

<BaseLayout>
  <template #header>
    <h1>Here might be a page title</h1>
  </template>

  <template #default>
    <p>A paragraph for the main content.</p>
    <p>And another one.</p>
  </template>

  <template #footer>
    <p>Here's some contact info</p>
  </template>
</BaseLayout>
```

### props逐级透传的问题解决：provide和inject

- 一个父组件相对于其所有的后代组件，会作为依赖提供者。任何后代的组件树，无论层级有多深，都可以注入由父组件提供给整条链路的依赖
- 要为组件后代提供数据，需要使用到 provide 选项
```
export default {
  provide: {
    message: 'hello!'
  }
}

```
- 注入上层组件提供的数据，需使用 inject 选项来声明
- 注入会在组件自身的状态之前被解析，因此你可以在 data() 中访问到注入的属性
```
export default {
  inject: ['message'],
  created() {
    console.log(this.message) // injected value
  }
}

```

### defineAsyncComponent可以实现异步组件的调用
- defineAsyncComponent 方法接收一个返回 Promise 的加载函数。这个 Promise 的 resolve 回调方法应该在从服务器获得组件定义时调用。你也可以调用 reject(reason) 表明加载失败。

### 自定义指令

- 在每一个 vue 组件中，可以在 directives 节点下声明私有自定义指令。

```vue
directives: { color: { // 为绑定的HTML元素设置红色的文字 bind(el){ //
形参的el是绑定了此指令的,原生的DOM对象 el.style.color = 'red' } } }
```

- update 函数: bind 函数只调用 1 次,当指令第一次绑定到元素时调用,当 DOM 更新时,bind 函数不会被触发。update 函数会在每次 DOM 更新时被调用。

```vue
directives: { color: { // 当指令第一次被绑定到元素时被调用 bind(el, binding){
el.style.color = binding.value }, // 每次DOM更新时被调用 update(el, binding){
el.style.color = binding.value } } }
```

- 全局自定义指令：需要通过 Vue.directive()进行声明

```vue
// 参数1: 字符串，表示全局自定义指令的名字 // 参数2： 对象，用来接收指令的参数值
Vue.directive('color',function(el, binding){ el.style.color = binding.value })
```

### 路由

- 路由的概念:就是对应关系
- SPA 指的是一个 web 网站只有唯一的 HTML 页面，所有组件的展示与切换都在这唯一的页面内完成。此时，不同组件之间的切换需要通过前端路由来实现
- 在 SPA 项目中，不同功能之间的切换，要依赖前端路由来完成。
- 前端路由的工作方式:
  - 用户点击了页面上的路由链接
  - 导致了 URL 地址栏中的 Hash 值发生了变化
  - 前端路由监听到了 Hash 地址的变化
  - 前端路由把当前 Hash 地址对应的组件渲染到浏览器中
- 实现简单的前端路由
  - 步骤 1: 通过 component 标签，结合 comName 动态渲染组件，示例代码如下:
  ```
      <component :is='comName'></component>
      export default {
          name: 'App'.
          data(){
              return {
                  comName: 'Home'
              }
          }
      }
  - 在created函数中，只要当前的App组件一旦被创建，就立即监听window对象的onhashchange事件，根据location.hash获取到的链接值进行判断修改对应的comName
  ```
- vue-router 是 vue.js 官方给出的路由解决方案，它只能结合 vue 项目进行使用，能够轻松管理 SPA 项目中的组件的切换。
- vue-router 安装和配置的步骤

  - 安装 vue-router 包
    - npm i vue-router@3.5.2 -S
  - 创建路由模块

    - 在 src 源代码目录下，新建 router/index.js 路由模块，并初始化如下的代码

    ```vue
    //导入Vue和VueRouter的包 import Vue from 'vue' import VueRouter from
    'vue-router' //2. 调用Vue.use()函数，把VueRouter安装为Vue的插件
    Vue.use(VueRouter) //3. 创建路由的实例对象 const router = new VueRouter()
    //创建路由的实例对象 const router = new VueRouter({ routes: [ { path:
    '/',redirect: '/home' }, { path: '/home', component: Home }, { path:
    '/movie',component: Movie }, { path: '/about', component: About, children: [
    { path:'tab1', component: Tab1},//2 访问/about/tab1 { path: 'tab2',
    component:Tab2} //2 访问/about/tab2 ] } ] }) //4. 向外共享路由的实例对象
    export default router
    ```

  - 导入并挂载路由模块

    ```Vue
        在main.js中导入并挂载router
        import Vue from 'vue'
        import App from './App'

        import router from '@/router/index.js'
        import 'bootstrap/dist/css/bootstrap.min.css'

        import '@/assets/global.css'
        Vue.config.productTip = false

        new Vue({
            render: h => h(App),

            // 在Vue项目中，要想把路由用起来，必须把路由实例对象
            // 通过下面的方式进行挂载.router为路由的实例对象
            router: router
        })
    ```

  - 声明路由链接和占位符

  ```
      <router-link to='/home'>首页</router-link>
      <router-link to='/movie'>电影</router-link>
      <router-link to='/about'>关于</router-link>
      <router-view></router-view>
  ```

- 路由重定向指的是: 用户在访问地址 A 的时候，强制用户跳转到地址 C,从而展示特定的组件页面。通过路由规则的 redirect 属性，指定一个新的路由地址，可以很方便地设置路由的重定向。
- 通过路由实现组件的嵌套，叫做嵌套路由，点击父级路由链接显示模板内容。

### 透传 Attributes

- “透传 attribute”指的是传递给一个组件，却没有被该组件声明为 props 或 emits 的 attribute 或者 v-on 事件监听器。最常见的例子就是 class、style 和 id
- 如果你不想要一个组件自动地继承 attribute，你可以在组件选项中设置 inheritAttrs: false。
- 如果需要，你可以通过 $attrs 这个实例属性来访问组件的所有透传 attribute

### Vue插件的相关使用

- 插件发挥作用的使用场景
	- 通过app.component()或者app.derective()注册一到多个全局组件和自定义指令
	- 通过app.provide()使得一个资源可以被注入整个应用
	- 想app.config.globalProperties添加一些全局实例属性和方法
	- 使用相关的组件库，如vue-router


### keyalive属性
- <KeepAlive> 是一个内置组件，它的功能是在多个组件间动态切换时缓存被移除的组件实例。
- <KeepAlive> 默认会缓存内部的所有组件实例，但我们可以通过 include 和 exclude prop 来定制该行为。这两个 prop 的值都可以是一个以英文逗号分隔的字符串、一个正则表达式，或是包含这两种类型的一个数组
- max:用来配置缓存的最大实例数

### Teleport传送们
- 是一个内置组件，它可以将一个组件内部的一部分模板“传送”到该组件的 DOM 结构外层的位置去
- 实例代码如下:
```
	<button @click="open = true">Open Modal</button>

	<Teleport to="body">
	  <div v-if="open" class="modal">
		<p>Hello from the modal!</p>
		<button @click="open = false">Close</button>
	  </div>
	</Teleport>

```

### Suspend异步组件
- <Suspense> 组件会触发三个事件：pending、resolve 和 fallback。pending 事件是在进入挂起状态时触发。
- resolve 事件是在 default 插槽完成获取新内容时触发。fallback 事件则是在 fallback 插槽的内容显示时触发


## Vue 组合式API相关内容

### 响应式基础

- 可以使用 reactive() 函数创建一个响应式对象或数组,响应式的代理对象和原始对象是不相等的
```
  import { reactive } from 'vue'
  const state = reactive({ count: 0 })
```
- 要在组件模板中使用响应式状态，需要在 setup() 函数中定义并返回
```
  import { reactive } from 'vue'

  export default {
    // `setup` 是一个专门用于组合式 API 的特殊钩子函数
    setup() {
      const state = reactive({ count: 0 })

      // 暴露 state 到模板
      return {
        state
      }
    }
  }

```
- 为保证访问代理的一致性，对同一个原始对象调用 reactive() 会总是返回同样的代理对象，而对一个已存在的代理对象调用 reactive() 会返回其本身
- reactive() 的局限性：
  - 仅对对象类型有效，对原始类型无效
  - 因为 Vue 的响应式系统是通过属性访问进行追踪的，因此我们必须始终保持对该响应式对象的相同引用。这意味着我们不可以随意地“替换”一个响应式对象，因为这将导致对初始引用的响应性连接丢失
- 当将响应式的对象解构为本地变量时，解构的变量将会失去响应性
- ref
  - Vue 提供了一个 ref() 方法来允许我们创建可以使用任何值类型的响应式 ref
  - ref 被传递给函数或是从一般对象上被解构时，不会丢失响应性
  - ref() 将传入参数的值包装为一个带 .value 属性的 ref 对象
  ```
    const count = ref(0)

    console.log(count) // { value: 0 }
    console.log(count.value) // 0

    count.value++
    console.log(count.value) // 1
  ```

## ES 模块化

### node.js 中如何实现模块化

- node.js 遵循了 CommonJS 模块化规范。其中:
  - 导入其他模块使用 require()方法
  - 模块对外共享成员使用 module.exports 对象
- 模块化的好处: 大家都遵循同样的模块化规范写代码，降低了沟通的成本，极大方便了各个模块之间的相互调用，利人利己。
- 前端模块化规范的分类:
  - 在 ES6 模块化规范诞生之前，JavaScript 社区已经尝试提出了 AMD,CMD,CommonJS 等模块化规范。但是这些社区提出的模块化标准，还存在一定的差异性和局限性，并不是浏览器和服务器通用的模块化。
  - AMD 和 CMD 适用于浏览器端的 Javascript 模块化
  - CommonJS 适用于服务器端的 Javascript 模块化
  - ES6 模块化规范是浏览器端与服务器端通用的模块化开发规范。它的出现极大的降低了前端开发者的模块化学习成本，开发者不需要再额外学习 AMD,CMD 或者 CommonJS 等模块化规范
- ES6 模块化规范中的定义:
  - 每一个 js 文件都是一个独立的模块
  - 导入其他模块成员使用 import 关键字
  - 向外共享模块成员使用 export 关键字
- 在 node.js 中默认仅仅支持 CommonJS 模块化规范，若想基于 node.js 体验与学习 ES6 的模块化语法，可以按照如下两个步骤进行配置:
  - 确保安装了 v14.15.1 或者更高版本的 node.js
  - 在 package.json 的根节点中添加"type":"module"节点
- 默认导出与默认导入
  - 每一个模块中，只允许使用唯一的一次 export default，否则会报错
  - 默认导入时的接收名称可以是任意名称，只要是合法的成员名称即可

```js
// 默认导出
let n1 = 10; //定义模块化私有成员n1
let n2 = 20; //定义模块化私有成员n2
function show() {} //定义模块私有方法show

export default {
  n1,
  show,
};

// 默认导入

import m1 from "./01_m1.js";

// 打印出输出结果;
console.log(m1);
```

- 按需导出和按需导入
  - 每一个模块中可以使用多次按需导出
  - 按需导入的成员名称必须和按需导出的名称保持一致
  - 按需导入时，可以使用 as 关键字进行重命名
  - 按需导入可以和默认导入一起使用

```js
export let s1 = "aaa";
```

```js
import { s1 } from "模块标识符";
```

- 直接导入并执行模块中的代码

  - 如果只想单纯的执行某个模块中的代码，并不需要得到模块中向外共享的成员。此时，可以直接导入并执行模块代码

  ```js
  // 当前模块为05_m3.js

  // 在当前模块中执行一个for循环操作

  for (let i = 0; i < 3; i++) {
    console.log(i);
  }

  //------------------分割线--------------
  // 直接导入并执行模块代码，不需要得到模块向外共享的成员

  import "./05_m3.js";
  ```

## Promise

### 回调地狱

- 多层回调函数的相互嵌套，就形成了回调地狱

```js
setTimeout(() => {
  console.log("延时1s后输出");
  setTimeout(() => {
    console.log("再延时2秒后输出");
  }, 2000);
}, 1000);
```

- 代码耦合性太强，牵一发而动全身，难以维护
- 大量冗余的代码相互嵌套，代码的可读性变差
- 为了解决回调地狱的问题，使用了 Promise

### Promise 的基本概念

- Promise 是一个构造函数
  - 我们可以创建 Promise 的实例 const p = new Promise()
  - new 出来的 Promise 实例对象，代表一个异步操作。
- Promise.prototype 上包含一个 then()方法
  - 每一次 new Promise()构造函数得到的实例对象
  - 都可以通过原型链的方式访问.then()方法，如 p.then()
  - .then()方法用来预先指定成功和失败的回调函数
    - p.then(成功的回调函数, 失败的回调函数)
    - p.then(result=>{}, error=>{})
    - 调用.then()方法时，成功的回调函数是必选的，失败的回调函数是可选的
- 基于回调函数按照顺序读取文件内容

```js
fs.readFile('./files/1.txt', 'utf8', (err, r1) => {
        if(err1) return console.log(err1, message)
        console.log(r1)
        fs.readFile('./files/2.txt', 'utf8', (err, r2) => {
            if(err2) return console.log(err2, message)
            console.log(r2)
        ]})
    ]})
```

- 基于 then-fs 读取文件内容
  - 由于 node.js 官方提供的 fs 模块仅支持以回调函数的方式读取文件，不支持 Promise 调用方式。因此，需要先运行如下的命令，安装 then-fs 这个第三方包，从而支持我们基于 Promise 的方式读取文件内容 npm install then-fs
- then-fs 的基本使用 -调用 then-fs 提供的 readFile()方法，可以异步地读取文件的内容，它的返回值是 Promise 的实例对象。因此可以调用.then()方法为每一个 Promise 异步操作指定成功和失败之后的回调函数。

```js
    import thenFs from 'then-fs'
    // 注意 .then()中失败回调是可选的，可以被省略
    thenFs.readFile('./files/1.txt','utf8').then(r1=>{console.log(r1)},err=>{console.log(err1.message)]}))
    thenFs.readFile('./files/2.txt','utf8').then(r2=>{console.log(r2)},err=>{console.log(err2.message)]}))
    thenFs.readFile('./files/3.txt','utf8').then(r3=>{console.log(r3)},err=>{console.log(err3.message)]}))

```

- then()方法的特性
  - 如果上一个.then()方法中返回了一个新的 Promise 实例对象，则可以通过下一个.then()继续进行处理。通过.then()方法的链式调用，就解决了回调地狱的问题
  ```js
  thenFs
    .readFile("./files/1.txt", "utf8")
    .then((r1) => {
      console.log(r1);
      return thenFs.readFile("./files/2.txt", "utf8");
    })
    .then((r2) => {
      console.log(r2);
      return thenFs.readFile("./files/3.txt", "utf8");
    })
    .then((r3) => {
      console.log(r3);
    })
    .catch((err) => {
      console.log(err, message);
    });
  ```
- Promise.all()方法会发起并行的 Promise 异步操作，等所有的异步操作全部结束后才会执行下一步的.then 操作(等待机制)

```js
    const promiseArr = [
        thenFs.readFile('./files/1.txt', 'utf-8')
        thenFs.readFile('./files/2.txt', 'utf-8')
        thenFs.readFile('./files/3.txt', 'utf-8')
    ]

    // 2.将Promise的数组，作为Promise.all()的参数
    Promise.all(promiseArr).then(([r1, r2, r3]) =>{
        console.log(r1, r2, r3)
    }).catch(err => {
        console.log(err, message)
    })
    // 数组中实例的顺序就是最终结果的顺序
```

- Promise.race()方法会发起并行的 Promise 异步操作，只要任何一个异步操作完成，就立即执行下一步的.then()操作。

```js

 const promiseArr = [
        thenFs.readFile('./files/1.txt', 'utf-8')
        thenFs.readFile('./files/2.txt', 'utf-8')
        thenFs.readFile('./files/3.txt', 'utf-8')
    ]

    // 2.将Promise的数组，作为Promise.all()的参数
    Promise.race(promiseArr).then(([r1, r2, r3]) =>{
        console.log(r1, r2, r3)
    }).catch(err => {
        console.log(err, message)
    })
    // 数组中实例的顺序就是最终结果的顺序
```

- 基于 Promise 封装异步读文件的方法

  - 如果想要创建具体的异步操作，则需要在 new Promise()构造函数期间，传递一个 function 函数，将具体的异步操作定义到 function 函数内部。

  ```js
  // 1. 方法的名称为getFile
  // 2.方法接收一个形参fpath,表示读取的文件路径
  function getFile(fpath) {
    // 3. 方法的返回值为Promise的实例对象
    return new Promise(function () {
      //4. 下面这行代码，表示这是一个具体的,读文件的异步操作
      fs.readFile(fpath, "utf-8", (err, dataStr) => {});
    });
  }
  ```

  - 通过.then()指定的成功和失败的回调函数，可以在 function 的形参中进行接收。

  ```js
  // resolve 形参是: 调用getFiles()方法时，通过.then指定"成功的"回调函数
  // reject 形参是: 调用getFiles()方法时，通过.then指定的"失败的"回调函数
  function getFile(fpath) {
    return new Promise(function (resolve, reject) {
      fs.readFile(fpath, "utf8", (err, dataStr) => {
        if (err) return reject(err);
        resolve(dataStr);
      });
    });
  }
  ```

### async/await

- async/await 是 ES8 引入的新语法，用来简化 Promise 异步操作。在 async/await 出现之前，开发者只能通过链式.then()的方式处理 Promise 异步操作
- then 链式调用的优点: 解决了回调地狱的问题。.then()链式调用的缺点:代码冗余，阅读性差，不易理解
- 使用 async/await 简化 Promise 异步操作

```js
import thenFs from "then-fs";

// 按照顺序读取文件1,2,3的内容

async function getAllFile() {
  const r1 = await thenFs.readFile("./files/1.txt", "utf-8");
  console.log(r1);
  const r2 = await thenFs.readFile("./files/2.txt", "utf-8");
  console.log(r2);
  const r3 = await thenFs.readFile("./files/3.txt", "utf-8");
  console.log(r3);
}
getAllFile();
```

- 注意事项
  - 如果在 function 中使用 await，则 function 必须被 async 修饰
  - 在 async 方法中，第一个 await 之前的代码会同步执行，await 之后的代码会异步执行

### EventLoop

- JavaScript 是单线程的语言，同一时间只能做一件事情
- 单线程执行任务队列的问题:如果前一个任务非常耗时，则后续的任务就不得不一直等待，从而导致程序假死的问题
- 同步任务和异步任务
  - 同步任务
    - 又叫非耗时任务，指的是在主线程上排队执行的那些任务
    - 只有前一个任务执行完毕，才能执行后一个任务
  - 异步任务
    - 又叫耗时任务，异步任务由 JavaScript 委托给宿主环境进行执行
    - 当异步任务执行完成后,会通知 JavaScript 主线程执行异步任务回调函数
  - 同步任务由 JavaScript 主线程次序执行
  - 异步任务委托给宿主环境执行
  - 已完成的异步任务对应的回调函数，会被加入到任务队列中等待执行
  - JavaScript 主线程的执行栈被清空后，会读取任务队列中的回调函数，依次执行
  - JavaScript 主线程不断重复上面的第四步
- JavaScript 主线程从"任务队列"中读取异步任务的回调函数，放到执行栈中依次执行。这个过程是循环不断的，所以整个的这种运行机制又称为 EventLoop(事件循环)
- JavaScript 把异步任务又做了进一步的划分，异步任务又分为两类:
  - 宏任务:异步 Ajax 请求，setTimeout,setInterval,文件操作，其他宏任务
  - 微任务: Promise.then catch 和 finally,process.nextTick, 其他微任务
  - 宏任务和微任务的执行顺序:每一个宏任务执行完成后，都会检查是否存在待执行的微任务，如果有，则执行完所有微任务之后，再继续执行下一个宏任务。

### Vue.config.js 配置文件介绍

- publicPath: 公共路径配置

```
module.exports = {
  publicPath: process.env.NODE_ENV === 'production'
    ? '/production-sub-path/'
    : '/'
}
```
