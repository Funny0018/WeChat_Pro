// pages/bills/bills.js
var objCart = new Array();
var price = '';
var app = getApp();
var pageLoding = true;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    deliveryAddress: JSON.parse('{}'),
    deliveryAddressID: '0',
    // deliveryAddress: JSON.parse('[{"faddress":"古洼一锅鲜(幸福城店)","fbuildno":"12313131啊啊啊","fcity":"河北省 廊坊市 广阳区","fid":"9","fname":"w w a啊","fphone":"22222222222"}]'),
    hasdeliveryAddress: false,
    cartInfo: JSON.parse('{}'),
    totalprice: '0',
    count: '0',
    dikouid: 0,
    dikoutitle: '抵扣券',
    dikouprice: '无可用抵扣券',
    dikoucolor: '#ddd',
    hongbaotitle: '红包',
    hongbaoprice: '无可用红包',
    hongbaocolor: '#ddd',
    finalProductPrice: '￥0',
    yunfei: '￥0',
    finalPrice: '￥0',
    fvipamount: '￥0',
    totalpricevip: '￥0',
    payprice: '￥0',
    viptext: '会员可节省',
    loading: false,
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
    pageLoding = true;
    this.setData({
      loading: false
    })
    if (this.data.deliveryAddressID == '0') {
      var oldaddressid = wx.getStorageSync('defaultAddressid');
      var addressid = (oldaddressid == '' ? '0' : oldaddressid);
      if (addressid != '0') {
        wx.request({
          url: app.globalData.appurl + '/wxGetDeliveryAddressByFid',
          data: {
            fid: addressid
          },
          success: function(res) {
            var obj = JSON.parse('[' + res.data.massage + ']');
            that.setData({
              deliveryAddress: obj,
              hasdeliveryAddress: true,
              deliveryAddressID: addressid,
              loading: true
            })
          }
        })
      } else {
        that.setData({
          loading: true
        })
      }
    } else {
      var addressid = this.data.deliveryAddressID;
      if (addressid != '0') {
        wx.request({
          url: app.globalData.appurl + '/wxGetDeliveryAddressByFid',
          data: {
            fid: addressid
          },
          success: function(res) {
            var obj = JSON.parse('[' + res.data.massage + ']');
            that.setData({
              deliveryAddress: obj,
              hasdeliveryAddress: true,
              deliveryAddressID: addressid,
              loading: true
            })
          }
        })
      } else {
        that.setData({
          loading: true
        })
      }
    }

    // , "totalpricevip": "' + this.data.totalpricevip + '", "totalpricevip": "' + this.data.viptext + '"
    // }';

    this.objCart = wx.getStorageSync('billProduct');
    var price = wx.getStorageSync('finalprice');
    var priceObj = JSON.parse(price);
    this.setData({
      totalprice: priceObj.totalPrice,
      dikouid: priceObj.dikou.id,
      dikoutitle: priceObj.dikou.title,
      dikouprice: priceObj.dikou.price,
      dikoucolor: priceObj.dikou.color,
      hongbaotitle: priceObj.hongbao.title,
      hongbaoprice: priceObj.hongbao.price,
      hongbaocolor: priceObj.hongbao.color,
      finalProductPrice: priceObj.finalProductPrice,
      yunfei: priceObj.yunfei,
      finalPrice: priceObj.finalPrice,
      fvipamount: priceObj.fvipamount,
      totalpricevip: priceObj.totalpricevip,
      viptext: priceObj.viptext,
      payprice: priceObj.payprice,
      // loading: true
    })
    var count = '0';
    for (var item in this.objCart) {
      count = count * 1 + this.objCart[item].count * 1;
    }
    var newObjCart = new Array();
    var i = 0;
    for (var item in this.objCart) {
      if (i < 4 && this.objCart[i].count > 0) {
        newObjCart.push(this.objCart[i]);
        i++;
      }
    }

    this.setData({
      cartInfo: newObjCart,
      count: count
    })

    // this.jisuan();
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
  toAddress: function(res) {
    wx.setStorageSync('ChooseAddress', this.deliveryAddressID);
    wx.navigateTo({
      // url: '/pages/billsChooseAddress/billsChooseAddress'

      url: '/pages/deliveryAddress/deliveryAddress'
    })
  },
  toProductInfo: function() {
    wx.setStorage({
      key: 'producntInfo',
      data: this.objCart
    })
    wx.navigateTo({
      url: '/pages/billProductInfo/billProductInfo'
    })
  },
  toFloat: function(res) {
    return (isNaN(parseFloat((res + '').replace('￥', '')))) ? 0 : parseFloat((res + '').replace('￥', ''))
  },
  jisuan: function() {
    var finalProductPrice = '￥' + (this.data.totalprice * 1 - this.toFloat(this.data.dikouprice) - this.toFloat(this.data.hongbaoprice));
    var finalPrice = '￥' + (this.toFloat(finalProductPrice) + this.toFloat(this.data.yunfei));
    if (this.toFloat(finalProductPrice) >= 29) {
      this.setData({
        yunfei: '免邮'
      })
    } else {
      this.setData({
        yunfei: '￥10'
      })
    }
    this.setData({
      finalProductPrice: finalProductPrice,
      finalPrice: finalPrice
    })
  },
  toPay: function() {
    console.log(
      pageLoding )
    if (pageLoding) {
      pageLoding = false;
      if (this.data.deliveryAddressID == 0) {
        wx.showModal({
          title: '',
          content: '请选择收货地址',
          showCancel: false,
          confirmColor: '#f60'
        })
      } else {
        var productinfo = '';
        var that = this;
        for (var item in this.objCart) {
          productinfo += '{"productId":"' + this.objCart[item].fProductId + '","count":"' + this.objCart[item].count + '"},'
        }
        if (productinfo.length > 0) {
          productinfo = productinfo.substring(0, productinfo.length - 1);
        }
        var info = '{"deliveryAddressId":"' + this.data.deliveryAddressID + '","totalprice":"' + this.data.totalprice + '","dikouprice":"' + this.data.dikouprice + '","dikouid":"' + this.data.dikouid + '","hongbaoprice":"' + this.data.hongbaoprice + '","yunfei":"' + this.data.yunfei + '","finalProductPrice":"' + this.data.finalProductPrice + '","fvipamount":"' + this.data.fvipamount + '","finalPrice":"' + this.data.finalPrice + '","userid":"' + app.globalData.userAppInfo.fuserid + '","products":[' + productinfo + '],"ftotalpricevip":"' + this.data.totalpricevip + '"}';
        wx.request({
          url: app.globalData.appurl + '/wxSaveBill',
          data: {
            data: info
          },
          success: function(res) {
            var billno = res.data.massage;
            console.log(billno);

            if (billno.indexOf("库存不足") >= 0) {
              pageLoding = true;
              wx.showModal({
                title: '',
                content: billno,
                showCancel: false,
                confirmColor: '#f60',


              })
            } else {
              wx.setStorage({
                key: 'billnoForOrder',
                data: billno,
              });
              var billobjcart = that.objCart;
              var oldobjcart = wx.getStorageSync('cartProduct');
              for (var old in oldobjcart) {
                for (var bill in billobjcart) {
                  if (billobjcart[bill].fProductId == oldobjcart[old].fProductId) {
                    delete oldobjcart[old];
                    // newobjcart.push(oldobjcart[old]);
                    break;
                  }
                }
              }
              var newobjcart = [];
              for (var item in oldobjcart) {
                if (oldobjcart[item] != undefined) {
                  newobjcart.push(oldobjcart[item]);
                }
              }
              wx.setStorageSync("cartProduct", newobjcart);
              wx.setStorage({
                key: 'billnoForOrderToPay',
                data: '1',
              });
              wx.navigateTo({
                url: '/pages/orderDetail/orderDetail',
              })
            }
          }
        })
      }
    }
  }
})