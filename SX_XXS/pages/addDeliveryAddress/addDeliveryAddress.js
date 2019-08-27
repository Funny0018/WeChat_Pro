// pages/addDeliveryAddress/addDeliveryAddress.js
var tcity = require("../..//static/script/citys.js");
var actionSheetList = '';
var appInstance = getApp();
var username = '';
var phone = '';
var buildno = '';
var addressid = '0';
var address = '';
var province = '';
var city = '';
var county = '';
Page({
  /**
   * 页面的初始数据
   */
  data: {
    actionSheetHidden: true,
    actionSheetItems: [],
    toastHidden: true,
    toastHiddenDelete: true,
    address: '',
    addressCity: '',
    condition: false,
    provinces: [],
    province: "",
    citys: [],
    city: "",
    countys: [],
    county: '',
    value: [0, 0, 0],
    values: [0, 0, 0],
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    tcity.init(that);
    var cityData = that.data.cityData;
    const provinces = [];
    const citys = [];
    const countys = [];
    for (let i = 0; i < cityData.length; i++) {
      provinces.push(cityData[i].name);
    }
    for (let i = 0; i < cityData[0].sub.length; i++) {
      citys.push(cityData[0].sub[i].name)
    }
    for (let i = 0; i < cityData[0].sub[0].sub.length; i++) {
      countys.push(cityData[0].sub[0].sub[i].name)
    }
    that.setData({
      'provinces': provinces,
      'citys': citys,
      'countys': countys,
      'province': cityData[0].name,
      'city': cityData[0].sub[0].name,
      'county': cityData[0].sub[0].sub[0].name
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var that = this;
    if (wx.getStorageSync('editAddress') != '0' && addressid != wx.getStorageSync('editAddress')) {
      addressid = wx.getStorageSync('editAddress');
      wx.request({
        url: appInstance.globalData.appurl + '/wxGetDeliveryAddressByFid',
        data: {
          fid: addressid
        },
        success: function (res) {
          var obj = JSON.parse(res.data.massage);
          that.username = obj.fname;
          that.phone = obj.fphone;
          that.buildno = obj.fbuildno;
          that.address = obj.faddress;
          that.setData({
            name: obj.fname,
            phone: obj.fphone,
            addressCity: obj.fcity,
            address: obj.faddress,
            buildno: obj.fbuildno,
          })
        }
      })
    }
  },
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  },
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  },
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  },
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  },
  showCity: function () {
    this.setData({
      condition: !this.data.condition
    })
  },
  bindItemTap: function (res) {
    this.setData({
      city: res.target.dataset.name,
      actionSheetHidden: !this.data.actionSheetHidden
    })
  },
  bindcancel: function (res) {
    this.setData({
      city: '',
      actionSheetHidden: !this.data.actionSheetHidden
    })
  },
  actionSheetChange: function (res) {
    this.setData({
      actionSheetHidden: !this.data.actionSheetHidden
    })
  },
  showAddress: function (res) {
    var cityNextPage = this.data.city + "";
    if (cityNextPage.split(' ').length > 1) {
      appInstance.globalData.deliveryAddrssCity = this.data.city.split(' ')[1];
    } else {
      appInstance.globalData.deliveryAddrssCity = this.data.city;
    }
    wx.navigateTo({
      url: '/pages/choseDeliveryAddress/choseDeliveryAddress'
    })
  },
  phoneinput: function (e) {
    this.phone = e.detail.value;
  },
  nameinput: function (e) {
    this.username = e.detail.value;
  },
  buildnoinput: function (e) {
    this.buildno = e.detail.value;
  },
  addressinput: function (e) {
    this.address = e.detail.value;
  },
  submit: function (e) {
    var that = this;
    if (this.username == undefined || this.username == '') {
      wx.showModal({
        title: '',
        content: '未填写收货人',
        showCancel: false,
      })
    } else if (this.phone == undefined || this.phone == '') {
      wx.showModal({
        title: '',
        content: '未填写收货电话',
        showCancel: false,
      })
    } else if (this.data.addressCity == undefined || this.data.addressCity == '' || this.data.addressCity.indexOf('未选择') != -1) {
      wx.showModal({
        title: '',
        content: '未选择城市',
        showCancel: false,
      })
    } else if (this.address == undefined || this.address == '') {
      wx.showModal({
        title: '',
        content: '未填写详细地址',
        showCancel: false,
      })
    } else if (this.buildno == undefined || this.buildno == '') {
      wx.showModal({
        title: '',
        content: '未填写楼号门牌号',
        showCancel: false,
      })
    } else if (appInstance.globalData.userAppInfo.fuserid == undefined || appInstance.globalData.userAppInfo.fuserid == '') {
      wx.showModal({
        title: '',
        content: '数据错误，请重新打开并登陆',
        showCancel: false,
      })
    } else {
      wx.request({
        url: appInstance.globalData.appurl + '/wxAddDeliveryAddress',
        data: {
          id: addressid,
          name: that.username,
          phone: that.phone,
          city: that.data.addressCity,
          address: that.address,
          buildno: that.buildno,
          userid: appInstance.globalData.userAppInfo.fuserid
        },
        success: function (res) {
          if (res.data.massage > 0) {
            that.setData({
              toastHidden: false
            })
          }
        }
      })
    }
  },
  toastChange: function () {
    wx.navigateBack({});
    wx.setStorageSync('editAddress', '0');
    addressid = '0';
  },
  bindDelete: function () {
    var that = this;
    wx.request({
      url: appInstance.globalData.appurl + '/wxDeleteDeliveryAddressByFid',
      data: {
        fid: addressid
      },
      success: function (res) {
        if (res.data.massage == 1) {
          that.setData({
            toastHiddenDelete: false
          })
        }
      }
    })
  },
  bindChange: function (e) {
    var val = e.detail.value
    var t = this.data.values;
    var cityData = this.data.cityData;
    if (val[0] != t[0]) {
      const citys = [];
      const countys = [];
      for (let i = 0; i < cityData[val[0]].sub.length; i++) {
        citys.push(cityData[val[0]].sub[i].name)
      }
      for (let i = 0; i < cityData[val[0]].sub[0].sub.length; i++) {
        countys.push(cityData[val[0]].sub[0].sub[i].name)
      }
      province = this.data.provinces[val[0]];
      city = cityData[val[0]].sub[0].name;
      county = cityData[val[0]].sub[0].sub[0].name;
      this.setData({
        province: this.data.provinces[val[0]],
        city: cityData[val[0]].sub[0].name,
        citys: citys,
        county: cityData[val[0]].sub[0].sub[0].name,
        countys: countys,
        values: val,
        value: [val[0], 0, 0],
        addressCity: province + ' ' + city + ' ' + county
      })
      return;
    }
    if (val[1] != t[1]) {
      const countys = [];
      for (let i = 0; i < cityData[val[0]].sub[val[1]].sub.length; i++) {
        countys.push(cityData[val[0]].sub[val[1]].sub[i].name)
      }
      city = this.data.citys[val[1]];
      county = cityData[val[0]].sub[val[1]].sub[0].name;
      this.setData({
        city: this.data.citys[val[1]],
        county: cityData[val[0]].sub[val[1]].sub[0].name,
        countys: countys,
        values: val,
        value: [val[0], val[1], 0],
        addressCity: province + ' ' + city + ' ' + county
      })
      return;
    }
    if (val[2] != t[2]) {
      county = this.data.countys[val[2]];
      this.setData({
        county: this.data.countys[val[2]],
        values: val,
        addressCity: province + ' ' + city + ' ' + county
      })
      return;
    }
  },
  open: function () {
    this.setData({
      condition: !this.data.condition
    })
  },
})