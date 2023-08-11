<template>
  <div class="q-pa-xl">
    <q-card>
      <q-card-section>
        <span class="text-h6">編輯動物資料</span>
      </q-card-section>
      <q-card-section>
        <div class="row q-col-gutter-xs">
          <div class="col-12 col-md-4">
            <q-input
              v-model="request.saveAnimal.data.animalName"
              label="動物暱稱/小名"
              filled
              clearable
            ></q-input>
          </div>
          <div class="col-12 col-md-4">
            <q-input
              v-model="request.saveAnimal.data.animalNumber"
              label="收容編號"
              filled
              clearable
            >
            </q-input>
          </div>
          <div class="col-12 col-md-4">
            <q-select
              v-model="request.saveAnimal.data.animalArea"
              label="所在區域"
              :options="area"
              filled
              clearable
            ></q-select>
          </div>
          <div class="col-12 col-md-4">
            <q-select
              v-model="request.saveAnimal.data.animalType"
              label="動物類別"
              :options="type"
              filled
              clearable
            ></q-select>
          </div>
          <div class="col-12 col-md-4">
            <q-input
              v-model="request.saveAnimal.data.animalCategory"
              autofocus
              label="動物品種"
              filled
              clearable
            >
            </q-input>
          </div>
          <div class="col-12 col-md-4">
            <q-select
              v-model="request.saveAnimal.data.animalGender"
              label="動物性別"
              :options="gender"
              filled
              clearable
            >
            </q-select>
          </div>
          <div class="col-12 col-md-4">
            <q-select
              v-model="request.saveAnimal.data.animalAge"
              label="動物年齡"
              :options="age"
              filled
              clearable
            ></q-select>
          </div>
          <div class="col-12 col-md-4">
            <q-input
              v-model="request.saveAnimal.data.animalColor"
              label="動物毛色"
              filled
              clearable
            >
            </q-input>
          </div>
          <div class="col-12 col-md-4">
            <q-input
              v-model="request.saveAnimal.data.animalDate"
              label="刊登日期"
              filled
              readonly
            >
            </q-input>
          </div>
          <div class="col-md-12">
            <q-input
              v-model="request.saveAnimal.data.animalDescribe"
              label="相關描述"
              filled
              type="textarea"
            />
          </div>
        </div>
        <q-separator spaced />
      </q-card-section>
      <q-card-section>
        <q-btn label="Pictures" color="secondary" @click="openFileInput">
          <input
            type="file"
            style="display: none"
            ref="fileInput"
            @change="fileUpload"
            accept="image/*"
            multiple="multiple"
          />
        </q-btn>
        <q-separator spaced />
        <div class="row">
          <div
            class="col-12 col-md-4"
            v-for="(img, index) in previewImage"
            :key="index"
          >
            <img style="width: 90%" :src="img" alt="Preview Image" />
            <div class="q-ma-md text-center">
              <q-btn @click="deleteimg(img)" label="X" color="red-5" />
            </div>
          </div>
        </div>
      </q-card-section>
      <q-card-section>
        <div class="q-ma-md text-center">
          <q-btn
            @click="callSaveAnimal"
            label="Submit"
            type="submit"
            color="primary"
          />
        </div>
      </q-card-section>
    </q-card>
  </div>
</template>

<script setup>
import { api } from "src/boot/axios";
import { ref, onMounted, reactive, onBeforeMount } from "vue";
import { useRouter, useRoute } from "vue-router";

const router = useRouter();
const route = useRoute();
const animalId = ref(route.params.id);
// let shelterAnimal = ref({});
// console.log(shelterAnimal.value.animalName);

let request = reactive({
  saveAnimal: {
    method: "POST",
    url: "/save",
    data: {},
  },
});

const today = new Date();
const date = ref(today.toISOString().slice(0, 10));
const gender = ref(["公", "母", "未知"]);
const type = ref(["貓", "狗", "兔", "鳥"]);
const area = ref(["北部區域", "中部區域", "南部區域", "東部區域", "離島區域"]);
const age = ref(["未離乳", "幼年", "成年", "老年"]);
const fileInput = ref(null);
const previewImage = ref([]);

const openFileInput = () => {
  fileInput.value.click();
};

const fileUpload = (event) => {
  const selectedFiles = event.target.files;
  if (previewImage.value.length + selectedFiles.length <= 3) {
    for (const file of selectedFiles) {
      const reader = new FileReader();
      reader.onload = (e) => {
        previewImage.value.push(e.target.result);
        request.saveAnimal.data.animalPhoto1 = previewImage.value[0];
        request.saveAnimal.data.animalPhoto2 = previewImage.value[1];
        request.saveAnimal.data.animalPhoto3 = previewImage.value[2];
      };
      reader.readAsDataURL(file);
    }
  } else {
    alert("最多只能上傳3張");
  }
};

const deleteimg = (img) => {
  previewImage.value = previewImage.value.filter((i) => i != img);
};

const callSaveAnimal = async () => {
  try {
    let response = await api(request.saveAnimal);
    alert("update successfully");
    router.push("/petpet");
  } catch (error) {
    alert("FAILED");
    console.log(error);
    console.log(request.saveAnimal.data);
  }
};

api.post(`/findByanimalId/${animalId.value}`).then((response) => {
  request.saveAnimal.data = response.data;
  previewImage.value.push(request.saveAnimal.data.animalPhoto1);
  previewImage.value.push(request.saveAnimal.data.animalPhoto2);
  previewImage.value.push(request.saveAnimal.data.animalPhoto3);
});
</script>
