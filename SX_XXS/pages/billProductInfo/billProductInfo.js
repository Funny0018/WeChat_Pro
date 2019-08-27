// pages/billProductInfo/billProductInfo.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    productInfo: ''
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
    // var objCart = wx.getStorageSync('billProduct');
    var objCart = wx.getStorageSync('producntInfo');
    var cartInfo = '';
    for (var item in objCart) {
      cartInfo += objCart[item].fProductId + ",";
    }
    if (cartInfo != "") {
      cartInfo = cartInfo.substr(0, cartInfo.length - 1);
    }
    wx.request({
      url: app.globalData.appurl + '/wxGetCartProductInfo',
      data: {
        ids: cartInfo
      },
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
      success: function(res) {
        var productInfo = JSON.parse(res.data.massage);
        for (var item in productInfo) {
          for (var items in objCart) {
            if (productInfo[item].fProductId == objCart[items].fProductId) {
              productInfo[item].count = (objCart[items].count * 1).toFixed(0);
            }
          }
        }
        that.setData({
          productInfo: productInfo
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

  }
})