package com.buffup.buffsdk.repo

import com.buffup.buffsdk.model.response.BuffResponse
import com.buffup.buffsdk.model.response.Result
import com.buffup.buffsdk.model.view.AuthorData
import com.buffup.buffsdk.model.view.BuffViewData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class FakeBuffRepository {
    private val fakeData = Gson().fromJson<BuffResponse<Result>>(
        str,
        TypeToken.getParameterized(BuffResponse::class.java, Result::class.java).type
    ).result.run {
        BuffViewData(
            this.answers,
            AuthorData("${this.author.firstName} ${this.author.lastName}", this.author.image),
            this.id,
            this.question,
            this.timeToShow
        )
    }

    fun simpleFakeBuff() = fakeData

    val errorMessage = "some error happen !"

    fun errorData(): BuffResponse<Result> = throw RuntimeException(errorMessage)

    // fake the real function
    fun getBuff(buffId: Int): BuffViewData {
        runBlocking { delay(1000) }
        return fakeData
    }
}

val str = "{" +
        "            \"result\": {" +
        "            \"id\": 5," +
        "            \"client_id\": 1," +
        "            \"stream_id\": 11," +
        "            \"time_to_show\": 15," +
        "            \"priority\": 3," +
        "            \"created_at\": \"2020-02-21T13:08:58.233866Z\"," +
        "            \"author\": {" +
        "            \"first_name\": \"Ronaldo\"," +
        "            \"last_name\": \"\"," +
        "            \"image\": \"https://res.cloudinary.com/dtgno0lg2/image/upload/v1582030534/avatars/Ronaldo_2x_yw2pnz.png\"" +
        "        }," +
        "            \"question\": {" +
        "            \"id\": 491," +
        "            \"title\": \"Kaio Jorge has 4 goals this tournament – I think he will score again today. What do you think?\"," +
        "            \"category\": 1" +
        "        }," +
        "            \"answers\": [" +
        "            {" +
        "                \"id\": 1163," +
        "                \"buff_id\": 0," +
        "                \"title\": \"No goals!\"," +
        "                \"image\": {" +
        "                \"0\": {" +
        "                \"id\": \"public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@1x.png\"," +
        "                \"key\": \"dc21866c51ba43938ab138b86d6c13b4\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@1x.png\"" +
        "            }," +
        "                \"1\": {" +
        "                \"id\": \"public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@2x.png\"," +
        "                \"key\": \"dc21866c51ba43938ab138b86d6c13b4\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@2x.png\"" +
        "            }," +
        "                \"2\": {" +
        "                \"id\": \"public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@3x.png\"," +
        "                \"key\": \"dc21866c51ba43938ab138b86d6c13b4\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/dc21866c51ba43938ab138b86d6c13b4/a-dc21866c51ba43938ab138b86d6c13b4@3x.png\"" +
        "            }" +
        "            }" +
        "            }," +
        "            {" +
        "                \"id\": 1164," +
        "                \"buff_id\": 0," +
        "                \"title\": \"One goal!\"," +
        "                \"image\": {" +
        "                \"0\": {" +
        "                \"id\": \"public/74287ffdf8d78838e2d669db7f2cbf53/a-74287ffdf8d78838e2d669db7f2cbf53@1x.png\"," +
        "                \"key\": \"74287ffdf8d78838e2d669db7f2cbf53\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/74287ffdf8d78838e2d669db7f2cbf53/a-74287ffdf8d78838e2d669db7f2cbf53@1x.png\"" +
        "            }," +
        "                \"1\": {" +
        "                \"id\": \"public/74287ffdf8d78838e2d669db7f2cbf53/a-74287ffdf8d78838e2d669db7f2cbf53@2x.png\"," +
        "                \"key\": \"74287ffdf8d78838e2d669db7f2cbf53\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/74287ffdf8d78838e2d669db7f2cbf53/a-74287ffdf8d78838e2d669db7f2cbf53@2x.png\"" +
        "            }," +
        "                \"2\": {" +
        "                \"id\": \"public/74287ffdf8d78838e2d669db7f2cbf53/a-74287ffdf8d78838e2d669db7f2cbf53@3x.png\"," +
        "                \"key\": \"74287ffdf8d78838e2d669db7f2cbf53\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/74287ffdf8d78838e2d669db7f2cbf53/a-74287ffdf8d78838e2d669db7f2cbf53@3x.png\"" +
        "            }" +
        "            }" +
        "            }," +
        "            {" +
        "                \"id\": 1165," +
        "                \"buff_id\": 0," +
        "                \"title\": \"Two or more!\"," +
        "                \"image\": {" +
        "                \"0\": {" +
        "                \"id\": \"public/043419866b39a8dd2c20b389aa45f55c/a-043419866b39a8dd2c20b389aa45f55c@1x.png\"," +
        "                \"key\": \"043419866b39a8dd2c20b389aa45f55c\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/043419866b39a8dd2c20b389aa45f55c/a-043419866b39a8dd2c20b389aa45f55c@1x.png\"" +
        "            }," +
        "                \"1\": {" +
        "                \"id\": \"public/043419866b39a8dd2c20b389aa45f55c/a-043419866b39a8dd2c20b389aa45f55c@2x.png\"," +
        "                \"key\": \"043419866b39a8dd2c20b389aa45f55c\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/043419866b39a8dd2c20b389aa45f55c/a-043419866b39a8dd2c20b389aa45f55c@2x.png\"" +
        "            }," +
        "                \"2\": {" +
        "                \"id\": \"public/043419866b39a8dd2c20b389aa45f55c/a-043419866b39a8dd2c20b389aa45f55c@3x.png\"," +
        "                \"key\": \"043419866b39a8dd2c20b389aa45f55c\"," +
        "                \"url\": \"https://buffup-s3-clients-s-31d41d8cd98f00b204e9800998ecf8427e.s3.eu-west-2.amazonaws.com/public/043419866b39a8dd2c20b389aa45f55c/a-043419866b39a8dd2c20b389aa45f55c@3x.png\"" +
        "            }" +
        "            }" +
        "            }" +
        "            ]," +
        "            \"language\": \"en\"" +
        "        }" +
        "        }"
