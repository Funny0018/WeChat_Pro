// pages/orderDetail/orderDetail.js
var app = getApp();
var billno = '';
Page({

  /**
   * 页面的初始数据
   */
  data: {
    addressname: '',
    addresscity: '',
    billno: '',
    billdate: '',
    productInfo: JSON.parse('{}'),
    count: 0,
    totalprice: '0',
    count: '0',
    dikoutitle: '抵扣券',
    dikouprice: '无可用抵扣券',
    dikoucolor: '#ddd',
    hongbaotitle: '红包',
    hongbaoprice: '无可用红包',
    hongbaocolor: '#ddd',
    finalProductPrice: '￥0',
    yunfei: '￥10',
    finalPrice: '￥0',
    fstate: 0,
    fvipamount: 0,
    allProductInfo: JSON.parse('{}'),
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
    this.billno = wx.getStorageSync("billnoForOrder");
    if (this.billno != '') {
      wx.request({
        url: app.globalData.appurl + '/wxGetBill',
        data: {
          billno: this.billno
        },
        success: function (res) {
          console.log(res.data.massage)
          var info = res.data.massage;
          var obj = JSON.parse(info);
          var count = 0;
          for (var item in obj.fproducts) {
            count = count * 1 + obj.fproducts[item].count * 1;
          }
          var finalProductInfo = new Array();;
          if (obj.fproducts.length > 5) {
            for (var i = 0; i < 5; i++) {
              finalProductInfo.push(obj.fproducts[i]);
            }
          } else {
            finalProductInfo = obj.fproducts
          }
          that.setData({
            addressname: obj.deliveryAddress.name,
            addresscity: obj.deliveryAddress.faddress,
            billno: obj.fbillno,
            billdate: obj.fbilldate,
            productInfo: finalProductInfo,
            count: count,
            totalprice: obj.totalprice,
            dikoutitle: obj.dikoutitle,
            dikouprice: obj.dikouprice == 0 ? '未使用抵扣' : '￥' + obj.dikouprice,
            dikoucolor: obj.dikouprice == 0 ? '#ddd' : '#ff6600',
            hongbaotitle: '红包',
            hongbaoprice: obj.hongbaoprice == 0 ? '未使用红包' : '￥' + obj.hongbaoprice,
            hongbaocolor: obj.hongbaoprice == 0 ? '#ddd' : '#f60',
            finalProductPrice: '￥' + obj.finalProductPrice,
            yunfei: obj.yunfei == 0 ? '包邮' : '￥' + obj.yunfei,
            finalPrice: '￥' + obj.finalPrice,
            fstate: obj.fstate,
            fvipamount: '￥' + obj.fvipamount,
            allProductInfo: obj.fproducts
          })
        }
      })
    }
    var toPay = wx.getStorageSync("billnoForOrderToPay");
    if (toPay == '1') {
      this.toPay();
    }
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {
    wx.setStorage({
      key: 'billnoForOrderToPay',
      data: '0',
    });
    wx.setStorage({
      key: 'billnoForOrder',
      data: '',
    });
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

    wx.setStorage({
      key: 'billnoForOrderToPay',
      data: '0',
    });
    wx.setStorage({
      key: 'billnoForOrder',
      data: '',
    });
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
  toProductInfo: function() {
    wx.setStorage({
      key: 'producntInfo',
      data: this.data.allProductInfo,
    })
    wx.navigateTo({
      url: '/pages/billProductInfo/billProductInfo'
    })
  },
  toPayBill: function() {
    this.toPay();
  },
  toPay: function() {
    var that = this;
    wx.request({
      url: app.globalData.appurl + '/wxPayBill',
      data: {
        billno: this.billno
      },
      success: function(res) {

        if (res.data.massage == '会员抵扣') {

          wx.setStorageSync('billnoForOrderToPay', 0);
          that.onShow();
        } else {
          var obj = JSON.parse(res.data.massage);
          wx.requestPayment({
            timeStamp: obj.timeStamp,
            nonceStr: obj.nonceStr,
            package: obj.package,
            signType: 'MD5',
            paySign: obj.paySign,
            success: function(res) {
              wx.switchTab({
                url: '/pages/product/product'
              })
            },
            fail: function(res) {
              var info = '{"fphonemodel":"' + app.globalData.phoneModel + '","fuserid":"' + app.globalData.userAppInfo.fuserid + '","fflag":"pay","ferrormsg":"' + res.errMsg + '"}';
              wx.request({
                url: app.globalData.appurl + '/wxSaveErrorLog',
                data: {
                  billno: info
                },
                success(res) {}
              })
            },
            complete: function(res) {}
          })
        }
      }
    })
  },
  toArrive: function() {
    wx.request({
      url: app.globalData.appurl + '/wxSetBillState',
      data: {
        billno: this.billno
      },
      success(res) {
        wx.navigateTo({
          url: '/pages/product/product',
        })
      }
    })
  }
})