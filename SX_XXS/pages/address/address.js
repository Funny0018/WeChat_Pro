// pages/address/address.js
var QQMapWX = require('../../static/script/qqmap-wx-jssdk.min.js');
var qqmapsdk;
var appInstance = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    province: appInstance.globalData.province,
    local: '',
    locals: '[{title:1,address:1},{title:1,address:1}]'
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
    this.flashLocal();

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
  flashLocal: function() {
    var that = this;
    wx.getLocation({
      type: 'wgs84',
      success: function(res) {
        qqmapsdk.reverseGeocoder({
          location: {
            latitude: res.latitude,
            longitude: res.longitude
          },
          get_poi: 1,
          poi_options: 'policy=2;radius=3000;page_size=15;page_index=1',
          success: function(addressRes) {
            appInstance.globalData.province = addressRes.result.address_component.city;
            if (appInstance.globalData.province == undefined) {
              appInstance.globalData.province = '';
            }
            var address = addressRes.result.address_component.district;
            address += addressRes.result.address_component.street;
            address += addressRes.result.address_component.street_number;
            var locals = addressRes.result.pois;
            if (address == '') {
              address = '未获取到当前位置'
            }
            that.setData({
              province: appInstance.globalData.province,
              local: address,
              locals: locals
            })
          },
          fail: function(res) {
          }

        })
      },
    })
  },
  reflash: function() {
    this.flashLocal();
  },
  tapAddress: function(res) {
    var addressName = res.currentTarget.id;
    appInstance.globalData.address = addressName;
    wx.navigateBack({})
  },
  inputChange: function(res) {
    var that = this;
    if (res.detail.value != '') {
      qqmapsdk.search({
        keyword: res.detail.value,
        city_name: appInstance.globalData.province,
        success: function(res) {
          that.setData({
            locals: res.data
          })
        },
        fail: function(res) {}
      })
    } else {
      this.flashLocal();
    }
  }
})