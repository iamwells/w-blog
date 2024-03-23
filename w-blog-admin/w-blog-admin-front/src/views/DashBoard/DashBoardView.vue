<script lang="ts" setup>
import { ref } from 'vue'
import { Dayjs } from 'dayjs'
import dayjs from 'dayjs'
import {} from 'ant-design-vue/locale/'
const calendarValue = ref<Dayjs>()
const selectedDate = ref<Dayjs>()
const cal = ref()
console.log(cal)

const onCalendarSelect = (value: Dayjs) => {
  selectedDate.value = value.locale(value.locale())
}
const onCalendarPanelChange = (value: Dayjs, mode: string) => {
  console.log(value, mode)
}

const resetDate = () => {
  const date = dayjs(Date.now())
  calendarValue.value = date
  selectedDate.value = date
}
</script>

<template>
  <div>
    <a-row :gutter="[16, 16]">
      <a-col :lg="6" :sm="12">
        <a-card>
          <a-statistic title="博客文章数" :value="0" suffix="篇">
            <template #prefix>
              <FileTextTwoTone two-tone-color="#aaa" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :lg="6" :sm="12">
        <a-card>
          <a-statistic title="作者人数" :value="0" suffix="人">
            <template #prefix>
              <HighlightTwoTone two-tone-color="#aaa" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :lg="6" :sm="12">
        <a-card>
          <a-statistic title="博客访问量" :value="0" suffix="次">
            <template #prefix>
              <FlagTwoTone two-tone-color="#aaa" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :lg="6" :sm="12">
        <a-card>
          <a-statistic title="博客状态" :value="'开发中'" suffix="">
            <template #prefix>
              <HeartTwoTone two-tone-color="#aaa" />
            </template>
          </a-statistic>
        </a-card>
      </a-col>
      <a-col :lg="12" :sm="24" :xs="24">
        <a-card title="最新文章" style="height: 322px; box-sizing: border-box"> 暂无 </a-card>
      </a-col>
      <a-col :lg="12" :sm="24">
        <div :style="{ border: '1px solid #f0f0f0', borderRadius: '4px' }">
          <a-calendar
            ref="cal"
            v-model:value="calendarValue"
            :fullscreen="false"
            @select="onCalendarSelect"
            @panelChange="onCalendarPanelChange"
            @mouseleave="resetDate"
          />
        </div>
      </a-col>
    </a-row>
    <a-float-button
      tooltip="撰写文章"
      :style="{
        right: '24px'
      }"
    >
      <template #icon>
        <FileAddTwoTone two-tone-color="#3177fc" />
      </template>
    </a-float-button>
  </div>
</template>

<style scoped></style>
