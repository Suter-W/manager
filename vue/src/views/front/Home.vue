<template>
  <div class="main-content">
    <div style="height: 60px; background-color: #C566F6FF"></div>
    <div style="display: flex">
      <div class="left"></div>
      <div style="width: 66%; background-color: white; height: 1000px">
        <div style="color: #FE0137FF; margin: 15px 0 15px 18px; font-weight: bold; font-size: 16px">主题市场</div>
        <div style="display: flex; margin: 0 25px; height: 550px">
          <div style="flex: 2">
            <div style="display: flex; color: #666666FF; margin: 14px 0" v-for="item in typeData">
              <img :src="item.img" alt="" style="height: 20px; width: 20px">
              <div style="margin-left: 10px; font-size: 14px"><a href="#" @click="navTo('/front/type?id=' + item.id)">{{item.name}}</a></div>
            </div>
          </div>
          <div style="flex: 5; margin-top: 15px">
            <div>
              <el-carousel height="300px" style="border-radius: 10px">
                <el-carousel-item v-for="item in carousel_top">
                  <img :src="item" alt="" style="width: 100%; height: 300px; border-radius: 10px">
                </el-carousel-item>
              </el-carousel>
            </div>
            <div style="margin-top: 30px; display: flex">
              <div style="flex: 1">
                <el-carousel height="300px" style="border-radius: 10px">
                  <el-carousel-item v-for="item in carousel_left">
                    <img :src="item" alt="" style="width: 100%; height: 200px; border-radius: 10px">
                  </el-carousel-item>
                </el-carousel>
              </div>
              <div style="flex: 1; margin-left: 5px">
                <el-carousel height="300px" style="border-radius: 10px">
                  <el-carousel-item v-for="item in carousel_right">
                    <img :src="item" alt="" style="width: 100%; height: 200px; border-radius: 10px">
                  </el-carousel-item>
                </el-carousel>
              </div>
            </div>
          </div>
          <div style="flex: 3; background-color: #F3F3F3FF; margin-top: 15px; margin-left: 15px; border-radius: 10px">
            <div style="text-align: center; margin-top: 30px">
              <img @click="navToPerson" :src="user.avatar" alt="" style="width: 80px; height: 80px; border-radius: 50%">
              <div style="margin-top: 10px">Hi，{{user.name}}</div>
            </div>
            <div style="margin-top: 20px; padding: 0 15px">
              <img src="@/assets/imgs/right.png" alt="" style="height: 150px; width: 100%; border-radius: 20px">
            </div>
            <div style="margin: 20px 10px 10px 10px; width: 250px; white-space: nowrap; overflow: hidden; text-overflow: ellipsis">
              <i class="el-icon-bell"></i>
              <span style="font-weight: bold">公告</span>
              <span style="color: #666666;">：{{ top }}</span>
            </div>
            <div style="display: flex; margin-top: 50px">
              <div style="flex: 1; text-align: center">
                <a href="#" @click="navTo('/front/collect')">
                  <img src="@/assets/imgs/收藏.png" alt="" style="height: 25px; width: 25px">
                  <div>我的收藏</div>
                </a>
              </div>
              <div style="flex: 1; text-align: center">
                <a href="#" @click="navTo('/front/address')">
                  <img src="@/assets/imgs/店铺.png" alt="" style="height: 25px; width: 25px">
                  <div>我的地址</div>
                </a>
              </div>
              <div style="flex: 1; text-align: center">
                <a href="#" @click="navTo('/front/cart')">
                  <img src="@/assets/imgs/购物车.png" alt="" style="height: 25px; width: 25px">
                  <div>购物车</div>
                </a>
              </div>
              <div style="flex: 1; text-align: center">
                <a href="#" @click="navTo('/front/orders')">
                  <img src="@/assets/imgs/订单.png" alt="" style="height: 25px; width: 25px">
                  <div>我的订单</div>
                </a>
              </div>
            </div>
          </div>
        </div>
        <div style="margin: 40px 0 0 15px; height: 40px; background-color: #04BF04FF; font-size: 20px; color: white; width: 130px; font-weight: bold; line-height: 40px; text-align: center; border-radius: 20px">热卖商品</div>
        <div style="margin: 10px 5px 0 5px">
          <el-row>
            <el-col :span="5" v-for="item in goodsData">
              <img @click="navTo('/front/detail?id=' + item.id)" :src="item.img" alt="" style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
              <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{item.name}}</div>
              <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{item.price}} / {{item.unit}}</div>
            </el-col>
          </el-row>
        </div>
        <div style="margin: 40px 0 0 15px; height: 40px; background-color: #04BF04FF; font-size: 20px; color: white; width: 130px; font-weight: bold; line-height: 40px; text-align: center; border-radius: 20px">猜你喜欢</div>
        <div style="margin: 10px 5px 0 5px">
          <el-row>
            <el-col :span="5" v-for="item in recommendData">
              <img @click="navTo('/front/detail?id=' + item.id)" :src="item.img" alt="" style="width: 100%; height: 175px; border-radius: 10px; border: #cccccc 1px solid">
              <div style="margin-top: 10px; font-weight: 500; font-size: 16px; width: 180px; color: #000000FF; text-overflow: ellipsis; overflow: hidden; white-space: nowrap;">{{item.name}}</div>
              <div style="margin-top: 5px; font-size: 20px; color: #FF5000FF">￥ {{item.price}} / {{item.unit}}</div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="right"></div>
    </div>
    <el-dialog title="新用户调研" :visible.sync="formVisible" width="50%" :close-on-click-modal="false" destroy-on-close>
      <el-form label-width="80px" ref="interestRef" style="padding-right: 10px;" :model="interestForm">
        <div style=" flex-wrap: wrap; justify-content: space-between;">
        <div style="width: 45%; margin-bottom: 15px;">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="interestForm.gender" placeholder="请选择性别" style="width: 100%">
              <el-option v-for="item in genderData" :label="item" :value="item" :key="item"></el-option>
            </el-select>
          </el-form-item>
          </div>
          <div style="width: 45%; margin-bottom: 15px;">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="interestForm.age" placeholder="年龄"></el-input>
            </el-form-item>
          </div>
          <div style="width: 45%; margin-bottom: 15px;">
            <el-form-item label="地区" prop="region">
              <el-select v-model="interestForm.region" placeholder="请选择地区" style="width: 100%">
                <el-option v-for="item in regionData" :label="item" :value="item" :key="item"></el-option>
              </el-select>
            </el-form-item>
          </div>
          <div style="width: 45%; margin-bottom: 15px;">
            <el-form-item label="职业" prop="occupation">
              <el-select v-model="interestForm.occupation" placeholder="请选择职业" style="width: 100%">
                <el-option v-for="item in occupationData" :label="item" :value="item" :key="item"></el-option>
              </el-select>
            </el-form-item>
          </div>

        </div>
        <el-form-item label="喜好" prop="interest">
          <el-checkbox-group v-model="selectedInterest" max="3" size="big">
            <el-checkbox-button v-for="item in typeData" :label="item.id" :key="item.id" style="border-color: grey">{{item.name}}</el-checkbox-button>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <div slot="footer" >
        <el-button size="medium" @click="cancelInterest">本次跳过</el-button>
        <el-button type="primary" @click="save" size="medium">确 定</el-button>
      </div>
    </el-dialog>
  </div>

</template>

<script>

export default {

  data() {
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      typeData: [],
      top: null,
      formVisible: false,
      notice: [],
      goodsData: [],
      recommendData: [],
      interestForm: {},
      genderData:["男","女"],
      regionData: [
        "北京市",
        "天津市",
        "河北省",
        "山西省",
        "内蒙古自治区",
        "辽宁省",
        "吉林省",
        "黑龙江省",
        "上海市",
        "江苏省",
        "浙江省",
        "安徽省",
        "福建省",
        "江西省",
        "山东省",
        "河南省",
        "湖北省",
        "湖南省",
        "广东省",
        "广西壮族自治区",
        "海南省",
        "重庆市",
        "四川省",
      ],
      occupationData: [
        "教师",
        "医生",
        "工程师",
        "律师",
        "会计师",
        "程序员",
        "销售员",
        "市场营销",
        "设计师",
        "金融从业者",
        "管理人员",
        "公务员",
        "农民",
        "建筑工人",
        "服务员",
        "学生",
        "其他职业",
      ],
      selectedInterest:[],
      carousel_top: [
        require('@/assets/imgs/carousel-1.png'),
        require('@/assets/imgs/carousel-2.png'),
        require('@/assets/imgs/carousel-9.png'),
      ],
      carousel_left: [
        require('@/assets/imgs/carousel-3.png'),
        require('@/assets/imgs/carousel-4.png'),
        require('@/assets/imgs/carousel-5.png'),
      ],
      carousel_right: [
        require('@/assets/imgs/carousel-6.png'),
        require('@/assets/imgs/carousel-7.png'),
        require('@/assets/imgs/carousel-8.png'),
      ],
    }
  },
  mounted() {
    this.loadType()
    this.loadNotice()
    this.loadGoods()
    this.loadRecommend()
    if(this.user.isNew === 1){
      this.formVisible = true
    }
  },
  // methods：本页面所有的点击事件或者其他函数定义区
  methods: {
    save(){
      this.saveInfo()
      this.saveInterest()
      this.formVisible = false
      this.user.isNew = 0
      localStorage.setItem("xm-user",JSON.stringify(this.user))
      location.reload()
    },
    saveInfo(){
      // alert(JSON.stringify(this.user.isNew))
      // alert(JSON.stringify(this.interestForm))
      this.interestForm.id = this.user.id
      this.$request({
        url: '/user/update',
        method: 'PUT',
        data: this.interestForm
      }).then(res =>{
        if(res.code === '200') {
          this.$message.success(('保存成功'))
        }
        else{
          this.$message.error(res.msg)
        }
      })
    },
    saveInterest(){
      // alert(JSON.stringify(this.selectedInterest))
      this.$request({
        url: '/interest/add',
        method: 'POST',
        data: {typeIds : this.selectedInterest}
      }).then(res=>{
        if(res.code === '200'){
          this.$message.success(('保存成功'))
        }
        else{
          this.$message.error(res.msg)
        }
      })
    },
    cancelInterest(){
      this.user.isNew = 0
      localStorage.setItem("xm-user",JSON.stringify(this.user))
    },
    loadRecommend(){
      this.$request.get('/goods/recommend').then(res => {
        if (res.code === '200') {
          this.recommendData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadType() {
      this.$request.get('/type/selectAll').then(res => {
        if (res.code === '200') {
          this.typeData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadGoods() {
      this.$request.get('/goods/selectTop15').then(res => {
        if (res.code === '200') {
          this.goodsData = res.data
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    loadNotice() {
      this.$request.get('/notice/selectAll').then(res => {
        this.notice = res.data
        let i = 0
        if (this.notice && this.notice.length) {
          this.top = this.notice[0].content
          setInterval(() => {
            this.top = this.notice[i].content
            i++
            if (i === this.notice.length) {
              i = 0
            }
          }, 2500)
        }
      })
    },
    navToPerson() {
      location.href = '/front/person'
    },
    navTo(url) {
      location.href = url
    },
  }
}
</script>

<style scoped>
.main-content {
  min-height: 100vh;
  /*overflow: hidden;*/
  background-size: 100%;
  background-image: url('@/assets/imgs/img.png');
}
.left {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/imgs/left-img.png');
}
.right {
  width: 17%;
  background-repeat: no-repeat;
  background-image: url('@/assets/imgs/right-img.png')
}
.el-col-5{
  width: 20%;
  max-width: 20%;
  padding: 10px 10px;
}

</style>