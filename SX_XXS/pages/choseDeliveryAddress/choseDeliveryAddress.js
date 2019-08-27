// pages/choseDeliveryAddress/choseDeliveryAddress.js

var appInstance = getApp();
var location = '';
var QQMapWX = require('../../static/script/qqmap-wx-jssdk.min.js');
var qqmapsdk;

Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    qqmapsdk = new QQMapWX({
      key: appInstance.globalData.qqMapKey
    });
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
    wx.request({
      url: appInstance.globalData.appurl + '/wxGetLoctionByCityName',
      header: {
        'content-type': 'application/x-www-form-urlencoded;charset=utf-8',
      },
      data: {
        fname: appInstance.globalData.deliveryAddrssCity
      },
      success: function(res) {
        that.location = res.data.massage;
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
  tapAddress: function(res) {
    var pages = getCurrentPages();
    if (pages[pages.length - 2]) {
      pages[pages.length - 2].setData({

        address: res.currentTarget.id.split('-')[0],
        city: res.currentTarget.id.split('-')[1]
      })
    }
    wx.navigateBack({})
  },
  inputChange: function(res) {
    var that = this;
    if (res.detail.value != '') {
      qqmapsdk.search({
        keyword: res.detail.value,
        location: that.location,
        page_size: '15',
        success: function(res) {
          for (var v in res.data) {
            res.data[v].ad_infos = res.data[v].ad_info.province + " " + res.data[v].ad_info.city + " " + res.data[v].ad_info.district;
          }
          that.setData({
            locals: res.data
          })
        },
        fail: function(res) {}
      })
    } else {
      that.setData({
        locals: null
      })
    }
  }
})