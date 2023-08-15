<template>
  <!-- 按鈕區塊 -->
  <div class="q-pa-xl">
    <q-card-section>
      <div class="row q-col-gutter-xs">
        <div class="col-12 col-md-2">
          <q-btn
            label="SEARCH ANIMALS"
            class="full-width"
            @click="callGetAnimals"
          />
        </div>
      </div>
      <q-separator spaced />
      <div class="row q-col-gutter-xs">
        <div class="col-12 col-md-4">
          <q-input
            v-model="request.getAnimals.params.animalName"
            label="動物名稱"
            filled
            clearable
          ></q-input>
        </div>
        <div class="col-12 col-md-4">
          <q-input
            v-model="request.getAnimals.params.animalNumber"
            label="收容編號"
            filled
            clearable
          ></q-input>
        </div>
        <div class="col-12 col-md-4">
          <q-select
            v-model="request.getAnimals.params.animalArea"
            label="所在區域"
            :options="area"
            filled
            clearable
          ></q-select>
        </div>
        <div class="col-12 col-md-4">
          <q-select
            v-model="request.getAnimals.params.animalType"
            label="動物類別"
            :options="type"
            filled
            clearable
          ></q-select>
        </div>
        <div class="col-12 col-md-4">
          <q-input
            v-model="request.getAnimals.params.animalCategory"
            label="動物品種"
            filled
            clearable
          ></q-input>
        </div>
        <div class="col-12 col-md-4">
          <q-select
            v-model="request.getAnimals.params.animalGender"
            label="動物性別"
            :options="gender"
            filled
            clearable
          ></q-select>
        </div>
        <div class="col-12 col-md-4">
          <q-select
            v-model="request.getAnimals.params.animalAge"
            label="動物年齡"
            :options="age"
            filled
            clearable
          ></q-select>
        </div>
        <div class="col-12 col-md-4">
          <q-input
            label="刊登起始日"
            filled
            clearable
            type="date"
            stack-label
            v-model="request.getAnimals.params.animalDateFrom"
          ></q-input>
        </div>
        <div class="col-12 col-md-4">
          <q-input
            label="刊登結束日"
            filled
            clearable
            type="date"
            stack-label
            v-model="request.getAnimals.params.animalDateTo"
          ></q-input>
        </div>
      </div>
    </q-card-section>

    <div class="row">
      <div class="col">
        <q-table
          title="Animals"
          :rows="shelterAnimal"
          :columns="columns"
          v-model:pagination="pagination"
          @request="alterPagination"
          selection="single"
          v-model:selected="selected"
          row-key="animalId"
        >
          <!-- @request觸發在切換分頁、排序、篩選表格時 -->
          <template #top-right>
            <q-btn
              round
              color="secondary"
              icon="cloud_upload"
              @click="addAnimal"
            />
            <q-separator spaced style="width: 10px" />
            <q-btn
              round
              color="amber"
              icon="edit"
              @click="editAnimal"
              v-show="selected.length"
            />
            <q-separator spaced style="width: 10px" />
            <q-btn
              round
              color="red-6"
              @click="calldeleteAnimals"
              icon="delete"
              v-show="selected.length"
            />
          </template>
        </q-table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { api } from "src/boot/axios";
import { ref, onMounted, reactive } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const gender = ref(["公", "母", "未知"]);
const type = ref(["貓", "狗", "兔", "鳥"]);
const area = ref(["北部區域", "中部區域", "南部區域", "東部區域", "離島區域"]);
const age = ref(["未離乳", "幼年", "成年", "老年"]);
let shelterAnimal = ref([]);
let pagination = ref([]);
let request = reactive({
  getAnimals: {
    method: "Get",
    url: "/search",
    params: {},
  },
  // params裡面放size可以指定分頁資料數
  deleteAnimals: {
    method: "DELETE",
    url: "/delete",
    data: {},
  },
});
let selected = ref([]);

// 最初載入給的值
const callGetAnimals = async () => {
  let response = await api(request.getAnimals);
  shelterAnimal.value = response.data.content;
  // response.data.content 為第一筆分頁的資料
  pagination.value.rowsNumber = response.data.totalElements;
  // pagination.value.rowsNumber = 1-20of(32)
  pagination.value.rowsPerPage = response.data.size;
  // 預設分頁為20筆一頁 response.data.size由網頁控制 pagination.value.rowsPerPage=records per page
  pagination.value.page = response.data.number + 1;
};

// 換頁or分頁數量(rowsPerPage) 例如page為第四頁 為了配合索引值-1才是正確頁數

const alterPagination = (arg) => {
  request.getAnimals.params.page = arg.pagination.page - 1;
  request.getAnimals.params.size = arg.pagination.rowsPerPage;
  callGetAnimals();
};

const addAnimal = () => {
  router.push("/addAnimal");
};

const editAnimal = () => {
  router.push({
    name: "editAnimal",
    params: { id: selected.value[0].animalId },
  });
};

const calldeleteAnimals = async () => {
  request.deleteAnimals.data.animalId = selected.value[0].animalId;
  try {
    await api(request.deleteAnimals);
    alert(`收容編號: ${selected.value[0].animalNumber} 已刪除`);
    router.go(0);
  } catch (error) {
    console.log(error);
  }
};

const columns = ref([
  // { name: "animalId", label: "Animal ID", field: (row) => row.animalId },
  { label: "動物名稱", field: (row) => row.animalName },
  { label: "收容編號", field: (row) => row.animalNumber },
  { label: "動物類別", field: (row) => row.animalType },
  { label: "動物品種", field: (row) => row.animalCategory },
  { label: "動物性別", field: (row) => row.animalGender },
  { label: "所在區域", field: (row) => row.animalArea },
  { label: "刊登日期", field: (row) => row.animalDate },
  { label: "描述", field: (row) => row.animalDescribe },
]);

onMounted(() => {
  callGetAnimals();
});
</script>
