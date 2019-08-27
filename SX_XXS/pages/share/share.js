// pages/share/share.js
var app = getApp();
var timer;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    first: true,
    fid: 0,
    userInfo: '',
    hour: 0,
    min: 0,
    sec: 0,
    userlose: 0,
    image: '',
    teamer: '',
    showRole: false,
    rank: '',
    shareCount: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    if (options.shareid != undefined) {
      this.setData({
        fid: options.shareid
      })
    }
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
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
    var shareid = 0;
    wx.request({
      url: app.globalData.appurl + '/wxGetShareRank',
      success: function(res) {
        that.setData({
          rank: JSON.parse(res.data.massage),
          shareCount: res.data.sharecount
        })
      }
    })
    app.getUserInfoForApp().then(function(res) {

      if (res.status == 200) {
        var shared = 0;
        shared = app.globalData.openshare;
        if (shared == 0) {
          wx.showModal({
            title: '分享活动',
            content: '活动还未正式开启，敬请期待！',
            showCancel: false,
            success: function() {
              wx.navigateBack({})
            }
          })
        } else {
          var logintimes = wx.getStorageSync("LoginTimes");
          // logintimes = 1;

          if (that.data.fid != 0) {
            if (logintimes > 1) {
              wx.showModal({
                title: '分享活动',
                content: '您并非新用户，只能创建分享活动',
                showCancel: false
              })
            } else {
              that.joinShare();
            }
          } else {
            that.getShare();
          }
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
  joinShare: function() {

    var that = this;
    wx.request({
      url: app.globalData.appurl + '/wxJoinShare',
      data: {
        openid: app.globalData.userAppInfo.fopenid,
        shareid: that.data.fid
      },
      success: function(res) {
        if (res.data.massage != "") {
          if (res.data.massage == '活动完成') {
            wx.showModal({
              title: '分享活动',
              content: '恭喜您完成活动！',
              showCancel: false
            })
          } else if (res.data.massage == '活动已过期') {
            wx.showModal({
              title: '分享活动',
              content: '活动已过期',
              showCancel: false,
            })

          } else {
            var obj = JSON.parse(res.data.massage);
            that.setData({
              fid: obj.fid,
              teamer: obj.joininfo,
              image: obj.userImage,
              userlose: obj.fuserlose,
              first: false,
              hour: obj.timedif.split(',')[0],
              min: obj.timedif.split(',')[1],
              sec: obj.timedif.split(',')[2],

            });
            that.timer = setInterval(function() {
              var hour = that.data.hour;
              var min = that.data.min;
              var sec = that.data.sec;
              if (sec * 1 == 0 && min * 1 == 0 && hour * 1 == 0) {

                that.setData({
                  hour: 0,
                  min: 0,
                  sec: 0
                })
              } else {
                if (sec * 1 == 0) {
                  sec = 59;
                  if (min * 1 == 0) {
                    min = 59;
                    if (hour * 1 == 0) {
                      clearInterval(timer)
                    } else {
                      hour = hour - 1;
                    }
                  } else {
                    min = min - 1;
                  }
                } else {
                  sec = sec - 1;
                }
                that.setData({
                  hour: hour,
                  min: min,
                  sec: sec
                })
              }
            }, 1000)
          }
        }
      }
    })
  },
  getShare: function() {
    var that = this;
    wx.request({
      url: app.globalData.appurl + '/wxGetShare',
      data: {
        fuserid: app.globalData.userAppInfo.fuserid
      },
      success: function(res) {
        if (res.data.massage != "") {
          var obj = JSON.parse(res.data.massage);
          that.setData({
            fid: obj.fid,

            teamer: obj.joininfo,
            image: obj.userImage,
            userlose: obj.fuserlose,
            first: false,
            hour: obj.timedif.split(',')[0],
            min: obj.timedif.split(',')[1],
            sec: obj.timedif.split(',')[2],

          });
          that.timer = setInterval(function() {
            var hour = that.data.hour;
            var min = that.data.min;
            var sec = that.data.sec;
            if (sec * 1 == 0 && min * 1 == 0 && hour * 1 == 0) {

              that.setData({
                hour: 0,
                min: 0,
                sec: 0
              })
            } else {
              if (sec * 1 == 0) {
                sec = 59;
                if (min * 1 == 0) {
                  min = 59;
                  if (hour * 1 == 0) {
                    clearInterval(timer)
                  } else {
                    hour = hour - 1;
                  }
                } else {
                  min = min - 1;
                }
              } else {
                sec = sec - 1;
              }
              that.setData({
                hour: hour,
                min: min,
                sec: sec
              })
            }
          }, 1000)
        }
      }
    })
  },
  onShareAppMessage: function(options) {
    var that = this;
    var userId = app.globalData.userAppInfo.fuserid;
    // path: '/pages/product/product?userId=' + userId, //这里拼接需要携带的参数


    var that = this;　　 // 设置菜单中的转发按钮触发转发事件时的转发内容

    var shareObj = {
      title: "邀请得好礼", // 默认是小程序的名称(可以写slogan等)
      path: '/pages/product/product?show=share&shareid=' + that.data.fid, //这里拼接需要携带的参数
      imageUrl: '',
      //自定义图片路径，可以是本地文件路径、代码包文件路径或者网络图片路径，支持PNG及JPG，不传入 imageUrl 则使用默认截图。显示图片长宽比是 5:4
      success: function(res) {　　　　　　 // 转发成功之后的回调

        if (res.errMsg == 'shareAppMessage:ok') {
        }
      },
      fail: function() {　　　　　　 // 转发失败之后的回调        　　　　　　
        if (res.errMsg == 'shareAppMessage:fail cancel') {
          // 用户取消转发
        } else if (res.errMsg == 'shareAppMessage:fail') {
          // 转发失败，其中 detail message 为详细失败信息
        }
      },
      complete: function() {
        // 转发结束之后的回调（转发成不成功都会执行） 
      }
    };
    // 来自页面内的按钮的转发

    if (options.from == 'button') {
      var eData = options.target.dataset;
      // 此处可以修改 shareObj 中的内容

      // shareObj.path = '/pages/btnname/btnname?btn_name=' + eData.name;　　
    }　　 // 返回shareObj

    return shareObj;
  },
  toScend: function() {
    var that = this;
    wx.showModal({
      title: '分享活动',
      content: '是否开启分享活动',
      showCancel: true,
      success: function(res) {
        if (res.confirm) {
          if ((app.globalData.userAppInfo.fisvip+app.globalData.bayApp) == 0) {
            wx.showModal({
              title: '分享活动',
              content: '您需要先开通会员才可发起活动。',
              showCancel: false
            })
          } else {
            wx.request({
              url: app.globalData.appurl + '/wxCreateShare',
              data: {
                fuserid: app.globalData.userAppInfo.fuserid
              },
              success: function(res) {
                if (res.data.massage == 'toMany') {
                  wx.showModal({
                    title: '分享活动',
                    content: '您在本月已完成三次分享活动',
                    showCancel: false
                  })
                } else {
                  var obj = JSON.parse(res.data.massage);
                  that.setData({
                    fid: obj.fid,
                    image: obj.userImage,
                    userlose: obj.fuserlose,
                    first: false,
                    hour: obj.timedif.split(',')[0],
                    min: obj.timedif.split(',')[1],
                    sec: obj.timedif.split(',')[2],

                  });
                  that.timer = setInterval(function() {
                    var hour = that.data.hour;
                    var min = that.data.min;
                    var sec = that.data.sec;
                    if (sec * 1 == 0 && min * 1 == 0 && hour * 1 == 0) {

                      that.setData({
                        hour: 0,
                        min: 0,
                        sec: 0
                      })
                    } else {
                      if (sec * 1 == 0) {
                        sec = 59;
                        if (min * 1 == 0) {
                          min = 59;
                          if (hour * 1 == 0) {
                            clearInterval(timer)
                          } else {
                            hour = hour - 1;
                          }
                        } else {
                          min = min - 1;
                        }
                      } else {
                        sec = sec - 1;
                      }
                      that.setData({
                        hour: hour,
                        min: min,
                        sec: sec
                      })
                    }
                  }, 1000)
                }
              }
            })
          }
        }
      }
    })

  },
  closeRole: function() {
    this.setData({
      showRole: false
    })
  },
  showRole: function() {
    this.setData({
      showRole: true
    })
  },
  showRank: function() {
    this.setData({
      fid: 0,
    })
    clearInterval(this.timer)
    wx.navigateTo({
      url: '/pages/shareRank/shareRank',
    })
  }
})