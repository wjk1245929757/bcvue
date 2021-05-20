#svg转换
#file_path  输入文件路径
#out_path   输出文件路径
#to         图片要转换的格式(pdf,png,ps)

svg_conversion <- function(file_path, out_path, to){
  library(rsvg)
  
  if(to == "pdf"){
    rsvg_pdf(file_path, out_path)
  }
  else if(to == "png"){
    rsvg_png(file_path, out_path)
  }
  else if(to == "ps"){
    rsvg_ps(file_path, out_path)
  }
  else{
    print("error!")
  }
}
# svg_conversion('E:/eclipse_workspace/biologicalCloudTools/public/UploadFolder/001@qq.com/20210520205915_001.svg','E:/eclipse_workspace/biologicalCloudTools/public/UserFolder/001@qq.com/20210520205915_001.png','png')

# svg_conversion("volcano.svg", "C:/Users/w1792/Desktop/svg_conversion_test.jpg", "jpg")