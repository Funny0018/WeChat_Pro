// pages/lucky-draw/lucky-draw.js
var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    // redEnvelopeList0: JSON.parse("[{\"fid\":\"1\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"2\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"3\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"4\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"5\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"6\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"7\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"8\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"9\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"10\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\",\"prize\":true}]"),
    // redEnvelopeList0: JSON.parse("[{\"fid\":\"4\",\"fname\":\"测试 - 商品11\",\"ftype\":\"2\",\"fproductID\":\"236\",\"fproductName\":\"SSGP 304不锈钢切西瓜神器\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 23 00: 00: 00\",\"fenddate\":\"2019 - 04 - 24 00: 00: 00\",\"fstate\":\"0\",\"foverdate\":\"0, 1, 1\",\"fminprice\":\"0.0\"},{\"fid\":\"3\",\"fname\":\"测试 - 金额1\",\"ftype\":\"1\",\"fproductID\":\"0\",\"fproductName\":\"0\",\"fprice\":\"12.0\",\"fstartdate\":\"2019 - 02 - 24 00: 00: 00\",\"fenddate\":\"2019 - 03 - 24 00: 00: 00\",\"fstate\":\"1\",\"foverdate\":\"0, 1, 0\",\"fminprice\":\"0.0\"},{\"fid\":\"2\",\"fname\":\"测试 - 礼品\",\"ftype\":\"2\",\"fproductID\":\"231\",\"fproductName\":\"云南金蜜2650g * 1个\",\"fprice\":\"0.0\",\"fstartdate\":\"2019 - 02 - 20 00: 00: 00\",\"fenddate\":\"2019 - 03 - 20 00: 00: 00\",\"fstate\":\"1\",\"foverdate\":\"0, 1, 0\",\"fminprice\":\"0.0\"}]"),
    redEnvelopeList0: "",
    animation0: -30,
    animation1: -30,
    animation2: -30,
    time0: 5,
    time1: 6.2,
    time2: 7.2,
    show: true,
    flashing: true,
    winInfo: [{
        date: "08-25",
        time: "14:28",
        phone: "135****6521",
        prize: "iPad大奖"
      },
      {
        date: "08-25",
        time: "14:28",
        phone: "135****6521",
        prize: "500元大红包"
      },
      {
        date: "08-25",
        time: "14:28",
        phone: "135****6521",
        prize: "iPad大奖"
      },
      {
        date: "08-25",
        time: "14:28",
        phone: "135****6521",
        prize: "500元大红包"
      }
    ],
    prizeShow: false,
    prizeList: new Array(30),
    QR: '',
    awardTime: 0,
    getAwardShow: false,
    getAward: "",
    loading: true
  },


  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    wx.request({
      url: app.globalData.appurl + '/wxGetAwardChanceInfo',
      success: function(res) {
        var obj = JSON.parse(res.data.massage);
        that.setData({
          redEnvelopeList0: obj,
          loading: false
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    if (app.globalData.userAppInfo != "") {
      app.updateAppInfo();
    }
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * @params sort 随机事件
   */
  sort(data) {
    //随机数组
    return data.sort((a, b) => {
      if (a.prize || b.prize) {

      } else {
        return a.fname.charCodeAt() + parseInt(Math.random() * 1000) > b.fname.charCodeAt() + parseInt(Math.random() * 1000)
      }
    })

  },
  shuffle: function(arr) {
    // var chance = JSON.parse("[{\"fid\":\"1\",\"fchange\":\"10\"},{\"fid\":\"2\",\"fchange\":\"20\"},{\"fid\":\"3\",\"fchange\":\"30\"},{\"fid\":\"4\",\"fchange\":\"40\"},{\"fid\":\"5\",\"fchange\":\"50\"},{\"fid\":\"6\",\"fchange\":\"60\"},{\"fid\":\"7\",\"fchange\":\"70\"},{\"fid\":\"8\",\"fchange\":\"80\"},{\"fid\":\"9\",\"fchange\":\"90\"},{\"fid\":\"10\",\"fchange\":\"10\"}]");
    var chance = arr;
    var award = parseInt(Math.random() * 100)
    var index = (((award >= 0) == true ? 1 : 0) +
      ((award >= chance[0]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[1]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[2]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[3]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[4]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[5]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[6]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[7]["fchange"] * 1) == true ? 1 : 0) +
      ((award >= chance[8]["fchange"] * 1) == true ? 1 : 0));
    var prize = chance[index - 1]["fcouponID"];
    // prize = 2;
    //完全随机
    var len = arr.length;
    for (var i = 0; i < len - 1; i++) {
      var index = parseInt(Math.random() * (len - i));
      var temp = arr[index];
      arr[index] = arr[len - i - 1];
      arr[len - i - 1] = temp;
    }
    var newArry = new Array();
    var taget = "";
    var replace = arr[4];
    for (var item in arr) {
      if (arr[item]["fcouponID"] == prize) {
        taget = arr[item];
        arr[item] = replace;
      }
    }
    arr[4] = taget;
    wx.setStorageSync("prize", arr[item]);
    var info = '{"fuserid":"' + app.globalData.userAppInfo.fuserid + '","faward":"' + prize + '"}';
    wx.request({
      url: app.globalData.appurl + '/wxSetAward',
      data: {
        info: info,
      },
      success: function(res) {
        console.log(res.data.massage);
      }
    })

    return arr;
  },
  /**
   * @params start 抽奖事件
   */
  start: function() {
    const that = this;
    if (that.data.awardTime > 0) {
      //  重置数组顺序后转动两圈

      this.setData({
        // redEnvelopeList0: that.sort(this.data.redEnvelopeList0)
        redEnvelopeList0: this.shuffle(this.data.redEnvelopeList0)
      }, () => {
        that.setData({
          animation0: this.data.animation0 + 720,
          awardTime: that.data.awardTime - 1
        })
      })
      var timer = setInterval(function() {
        that.setData({
          getAwardShow: true,
          getAward: "恭喜你获得" + wx.getStorageSync("prize").fname
        })
        clearInterval(timer)
      }, 3800)
    }
  },
  hideGetAward: function() {
    this.setData({
      getAwardShow: false,
      getAward: ""
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  showPrize: function() {
    this.setData({
      prizeShow: true
    });
  },
  closePrize: function() {
    this.setData({
      prizeShow: false
    });
  },
  /**
   * @params lamp 跑马灯封装
   */
  lamp: function() {
    let flashing = !this.data.flashing;
    this.setData({
      flashing: flashing
    }, () => {
      setTimeout(() => {
        this.lamp();
      }, 250);
    });
  },
  onReady: function() {
    this.lamp();
  },
  onShareAppMessage: function (options) {
    var that = this;
    var userId = app.globalData.userAppInfo.fuserid;
    // path: '/pages/product/product?userId=' + userId, //这里拼接需要携带的参数

    　　
    var that = this;　　 // 设置菜单中的转发按钮触发转发事件时的转发内容
    　　
    var shareObj = {　　　　
      title: "转发的标题", // 默认是小程序的名称(可以写slogan等)
      path: '/pages/product/product?userId=' + userId, //这里拼接需要携带的参数
      imageUrl: '',
      //自定义图片路径，可以是本地文件路径、代码包文件路径或者网络图片路径，支持PNG及JPG，不传入 imageUrl 则使用默认截图。显示图片长宽比是 5:4
      success: function(res) {　　　　　　 // 转发成功之后的回调
        
        console.log("-------------------------");　console.log(res);　
        if (res.errMsg == 'shareAppMessage:ok') {　
          console.log("成功");　　　　　
        }　　　　
      },
      fail: function() {　　　　　　 // 转发失败之后的回调        　　　　　　
        if (res.errMsg == 'shareAppMessage:fail cancel') {　　　　　　　　
          // 用户取消转发
          console.log("取消");　　　　　　　
        } else if (res.errMsg == 'shareAppMessage:fail') {　　　　　　　　
          // 转发失败，其中 detail message 为详细失败信息
          console.log("失败");　　　　　　　
        }　　　　
      },
      complete: function() {　
        // 转发结束之后的回调（转发成不成功都会执行） 
        console.log("完成");　　　　　　　　　　
      }　　
    };　　
    // 来自页面内的按钮的转发
    　　
    if (options.from == 'button') {　　　　
      var eData = options.target.dataset;
      console.log(options);　　　
      console.log(eData.name); // shareBtn
      　　　　 // 此处可以修改 shareObj 中的内容
      　　　　
      // shareObj.path = '/pages/btnname/btnname?btn_name=' + eData.name;　　
    }　　 // 返回shareObj
    　　
    return shareObj;
  }
});