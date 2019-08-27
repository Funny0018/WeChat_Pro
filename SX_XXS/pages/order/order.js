// pages/order/order.js
var app = getApp();
var pagesize = 5;
var currindex = 1;
var fstate = -1;
var maxPage = 0;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    billlist: JSON.parse('[]'),
    loading: true,
    isbottom: false,
    type: -1,
    maxCount:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

    this.pagesize = app.globalData.orderpageSize;
    this.fstate = -1;

    this.obj = '';
    this.maxPage = 0;
    this.currindex = 1;
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

    var type = wx.getStorageSync('orderType');
    this.fstate=type;

    this.obj = '';
    this.getOrderList();
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
    // if (this.maxPage >= this.currindex || this.maxPage == 0) {

    if (!this.data.isbottom) {
      this.currindex += 1;
      this.getOrderList();
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  getOrderList: function() {
    this.setData({
      loading: false
    })
    var that = this;
    var userid = app.globalData.userAppInfo.fuserid;
    wx.request({
      url: app.globalData.appurl + '/wxGetOrder',
      data: {
        pageSize: this.pagesize,
        crruPage: this.currindex,
        fstate: this.fstate,
        userid: userid
      },
      success: function (res) {
        var bills = JSON.parse(res.data.massage);
        that.maxPage = res.data.maxPage;
        var maxCount = res.data.maxCount;
        if (that.crruPages < that.maxPage) {
          that.setData({
            isbottom: false
          })
        } else {
          that.setData({
            isbottom: true
          })
        }
        if (that.obj == '') {
          that.obj = bills;
        } else {
          for (var item in bills) {
            that.obj.push(bills[item]);
          }
        }
        that.setData({
          billlist: that.obj,
          type: that.fstate,
          maxCount: maxCount
        })
        that.setData({
          loading: true
        })
      }
    })
  },
  toBillDetail: function(res) {
    var billno = res.currentTarget.id;
    wx.setStorage({
      key: 'billnoForOrder',
      data: billno,
    });
    wx.setStorage({
      key: 'billnoForOrderToPay',
      data: 0,
    })
    wx.navigateTo({
      url: '/pages/orderDetail/orderDetail',
    })
  },
  switchTab: function(res) {
    this.obj = '';
    this.currindex = 1;
    var fstate = res.currentTarget.id;
    this.fstate = fstate;
    this.getOrderList();
  }
})