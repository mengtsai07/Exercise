<template>
  <!-- 按鈕區塊 -->
  <q-card-section>
    <div class="row q-col-gutter-xs">
      <div class="col-12 col-md-2">
        <q-btn
          label="SEARCH EMPLOYEES"
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
          v-model="request.getAnimals.params.animalAge"
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

  <div class="q-pa-xl">
    <div class="row">
      <div class="col">
        <q-table
          title="Animals"
          :rows="shelterAnimal"
          :columns="columns"
          v-model:pagination="pagination"
          @requst="alertPagination"
          selection="single"
          v-model:selected="selected"
          row-key="animalId"
        >
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
let pagination = ref({});
let request = reactive({
  getAnimals: {
    method: "GET",
    url: "/search",
    params: {},
  },
  deleteAnimals: {
    method: "DELETE",
    url: "/delete",
    data: {},
  },
});
let selected = ref([]);

const callGetAnimals = async () => {
  let response = await api(request.getAnimals);
  shelterAnimal.value = response.data.content;
  pagination.value.rousNumber = response.data.totalElements;
  pagination.value.rousPerPage = response.data.size;
  pagination.value.page = response.data.number + 1;
};

const alertPagination = (args) => {
  request.getEmployees.params.page = args.pagination.page - 1;
  request.getEmployees.params.size = args.pagination.rowsPerPage;
  callGetAnimals();
};

const addAnimal = () => {
  router.push("/addAnimal");
};

const editAnimal = () => {
  // router.push({
  //   path: "/editAnimal",
  //   query: { animalid: selected.value[0].animalId },
  // });
  router.push({
    name: "editAnimal",
    params: { id: selected.value[0].animalId },
  });
};

const calldeleteAnimals = async () => {
  // console.log(selected.value[0].animalId);
  request.deleteAnimals.data.animalId = selected.value[0].animalId;
  try {
    await api(request.deleteAnimals);
    alert(`收容編號: ${request.deleteAnimals.data.animalNumber} 已刪除`);
    router.go(0);
  } catch (error) {
    console.log(error);
  }
};
const columns = ref([
  { label: "動物名稱", field: (row) => row.animalName },
  { label: "收容編號", field: (row) => row.animalNumber },
  { label: "動物類別", field: (row) => row.animalType },
  { label: "動物品種", field: (row) => row.animalCategory },
  { label: "動物性別", field: (row) => row.animalGender },
  { label: "刊登日期", field: (row) => row.animalDate },
  { label: "描述", field: (row) => row.animalDescribe },
]);

onMounted(() => {
  api.get("/shelteranimal").then((response) => {
    shelterAnimal.value = response.data.content;
  });
  // callGetAnimals();
});
</script>

<style lang="scss" scoped></style>
