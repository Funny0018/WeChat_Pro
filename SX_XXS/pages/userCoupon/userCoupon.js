// pages/userCoupon/userCoupon.js

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
        var obj = JSON.parse(res.data.massage)
        that.setData({
          couponList: obj
        })
      }
    })
  },
})