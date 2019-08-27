// pages/deliveryAddress/deliveryAddress.js
var appInstance = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // deliveryaddress: JSON.parse('[{"fid":"1","faddress":"AA国际动漫专营店斯塔克模型工作室","fbuildno":"2222","fcity":"河北省 沧州市 运河区","fname":"我的我的","fphone":"11111111111"}]')
    deliveryaddress: ''
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
    var userid = appInstance.globalData.userAppInfo.fuserid;
    var that = this;
    wx.request({
      url: appInstance.globalData.appurl + '/wxGetDeliveryAddress',
      data: {
        userid: userid
      },
      success: function(res) {
        if (res.data.massage != '') {
          var obj = JSON.parse(res.data.massage);
          that.setData({
            deliveryaddress: obj
          })
        }
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
  adddelivery: function() {
    wx.setStorageSync('editAddress', '0');
    wx.navigateTo({
      url: '/pages/addDeliveryAddress/addDeliveryAddress'
    })
  },
  editAddress: function(res) {

    var addressid = (res.currentTarget.id + '').split('-')[1];
    wx.setStorageSync(
      'editAddress',
      addressid
    )
    wx.navigateTo({
      url: '/pages/addDeliveryAddress/addDeliveryAddress'
    })
  },
  backAddress: function(res) {
    var addressid = (res.currentTarget.id + '').split('-')[1];
    var pages = getCurrentPages();
    var prePages = pages[pages.length - 2];
    if (prePages.route == 'pages/bills/bills') {
      prePages.setData({
        deliveryAddressID: addressid,
        // deliveryAddress: JSON.parse('[{"faddress":"古洼一锅鲜(幸福城店)","fbuildno":"12313131啊啊啊","fcity":"河北省 廊坊市 广阳区","fid":"9","fname":"w w a啊","fphone":"22222222222"}]'),
        hasdeliveryAddress: true
      });
      wx.setStorage({
        key: 'billaddressid',
        data: addressid
      })
      wx.setStorage({
        key: 'defaultAddressid',
        data: addressid
      })
      wx.navigateBack({

      })
    }
  }
})