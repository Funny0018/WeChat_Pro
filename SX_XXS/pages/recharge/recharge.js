// pages/recharge/recharge.js
var app = getApp();
var typeid = 1;
var navScrollLeft = 0;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    types: '',
    vipamountapp: 0,
    typeid: 1,
    fprice: 0,
    rechargeamount: false,
    inputvalue: '',
    navScrollLeft: 0,
    current: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

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
    var that = this;
    app.updateAppInfo();
    wx.request({
      url: app.globalData.appurl + '/wxGetRechargeType',
      success: function(res) {
        var type = JSON.parse(res.data.massage);
        for (var item in type) {
          if (type[item].fprice == "0") {
            type[item].fprice = "其他";
          } else {
            type[item].fprice += "元";
          }
        }
        that.setData({
          types: type,
          vipamountapp: app.globalData.userAppInfo.fmoney == null ? 0 : app.globalData.userAppInfo.fmoney,
          typeid: type[0].fid,
          fprice: type[0].fprice,
          rechargeamount: false
        })
      }
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  changeType: function(res) {
    var id = res.currentTarget.id.split('-')[1];
    var type = this.data.types;
    var price = 0;
    var current = 0;
    for (var item in type) {
      if (type[item].fid == id) {
        price = type[item].fprice;
        current = item;
      }
    }
    var rechargeamount;
    var inputvalue = 0;;
    if (id == 0) {
      rechargeamount = true;
      inputvalue = 0;
    } else {
      rechargeamount = false;
      inputvalue = 0;
    }
    if (price == "其他") {
      price = "0";
    }
    this.setData({
      typeid: id,
      fprice: price,
      rechargeamount: rechargeamount,
      inputvalue: 0,
      current: current
    })
  },
  recharge: function() {
    var info = '{"userid":' + app.globalData.userAppInfo.fuserid + ',"price":' + this.data.fprice.replace('元','')*1 + ',"rechargeType":"' + this.data.typeid + '"}';
    var that = this;
    wx.request({
      url: app.globalData.appurl + '/wxPayAmount',
      data: {
        info: info
      },
      success: function(res) {
        var obj = JSON.parse(res.data.massage);
        wx.requestPayment({
          timeStamp: obj.timeStamp,
          nonceStr: obj.nonceStr,
          package: obj.package,
          signType: 'MD5',
          paySign: obj.paySign,
          success: function(res) {
            app.globalData.userAppInfo.fmoney += that.data.fprice
            wx.switchTab({
              url: '/pages/me/me'
            })
          },
          fail: function(res) {},
          complete: function(res) {}
        })
      }
    })
  },
  inputprice: function(res) {
    var price = res.detail.value * 1;
    this.setData({
      inputvalue: price,
      fprice: price+'元'
    })
  },
  swiperTab: function(res) {
    var index = res.detail.current;
    var type = this.data.types;
    var id = type[index].fid;
    var price = 0;
    for (var item in type) {
      if (type[item].fid == id) {
        price = type[item].fprice;
      }
    }
    if (price == "其他") {
      price = "0";
    }
    var rechargeamount;
    var inputvalue = 0;;
    if (id == 0) {
      rechargeamount = true;
      inputvalue = 0;
    } else {
      rechargeamount = false;
      inputvalue = 0;
    }
    this.setData({
      typeid: id,
      fprice: price,
      rechargeamount: rechargeamount,
      inputvalue: 0
    })



  }

})