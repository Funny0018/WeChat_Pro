// pages/cartCoupon/cartCoupon.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    couponList: "",
    billPrice: 0
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
    app.getUserInfoForApp().then(function(res) {
      if (res.status == 200) {
        that.getCouponByUser();
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
  getCouponByUser: function() {
    var that = this;

    wx.request({
      url: app.globalData.appurl + '/wxGetUserCoupon',
      data: {
        "fuserid": app.globalData.userAppInfo.fuserid
        // "fuserid": '21'
      },
      success: function(res) {
        var hongbaoCount = res.data.count;
        var price = wx.getStorageSync('couponPrice');
        var obj = JSON.parse(res.data.massage)
        var objCart = wx.getStorageSync('cartProduct');
        for (var i in obj) {
          if (obj[i].canUse == '1') {
            if (obj[i].ftype == "金额抵扣") {
              if (obj[i].fminprice > price) {
                obj[i].canUse = 0;
                obj[i].useInfo = '低于可用金额';
              }
            } else if (obj[i].ftype == "商品抵扣") {
              obj[i].canUse = 0;
              obj[i].useInfo = '没有对应商品';
              for (var j in objCart) {
                if (objCart[j].fProductId == obj[i].fproductId) {
                  obj[i].canUse = 1;
                  obj[i].useInfo = '';
                }
              }
            }
          }
        }
        that.setData({
          couponList: obj
        })
      }
    })
  },
  choseCoupon: function(res) {
    var couponID = res.currentTarget.id;
    var info = "";
    for (var v in this.data.couponList) {
      if (this.data.couponList[v].fid == couponID) {
        if (this.data.couponList[v].ftype == "金额抵扣") {
          info = this.data.couponList[v].fid + '|' + this.data.couponList[v].fname + '|' + this.data.couponList[v].fprice;
        } else if (this.data.couponList[v].ftype == "商品抵扣") {
          info = this.data.couponList[v].fid + '|' + this.data.couponList[v].fname + '|' + ((app.globalData.userAppInfo.fisvip+app.globalData.bayVip) >0 ? this.data.couponList[v].fproductVipPrice : this.data.couponList[v].fproductPrice);
        }
      }
    }
    wx.setStorageSync("couponInfo", info);
    wx.navigateBack({

    })
  },
  clear: function() {
    wx.clearStorageSync("couponInfo");
    wx.navigateBack({

    })
  }
})