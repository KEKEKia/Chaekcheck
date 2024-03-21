import React from 'react';
import { FileUploader } from 'react-drag-drop-files';

// icons
import PhotoIcon from '../../assets/icons/photoIcon';
import CloseIcon from '../../assets/icons/closeIcon';
import { ImageUploadProps } from '../../interface/predict';

const fileTypes = ['JPG', 'PNG', 'GIF'];
function ImageUploader({
  currentImageIndex,
  imageList,
  isDrag,
  setImageList,
  setCurrentImageIndex,
  setIsDrag,
  imageRegistHandler,
}: ImageUploadProps) {
  const imageDeleteHandler = (index: number) => {
    setImageList([...imageList].filter((item, idx) => idx !== index));
    setCurrentImageIndex(currentImageIndex % imageList.length);
  };

  return (
    // 렌더링 파트
    <ul className="w-100 h-100 grid grid-cols-5 place-content-center ">
      {imageList.map((image, index) => (
        <button
          key={Math.random()}
          type="submit"
          onClick={() => {
            setCurrentImageIndex(index);
          }}>
          <li
            key={Math.random()}
            className="w-32 h-32 m-auto mt-6 group relative">
            <div className="absolute  hidden group-hover:block right-0">
              {/* <button type="button" onClick={() => imageDeleteHandler(index)}> */}
              <CloseIcon
                styleString="w-6 h-6 text-LOGO-600"
                clickMethod={(idx: number) => imageDeleteHandler(idx)}
                index={index}
              />
              {/* </button> */}
            </div>
            <img
              alt="갤러리 이미지"
              src={URL.createObjectURL(image)}
              className="h-full rounded-2xl bg-contain m-auto"
            />
          </li>
        </button>
      ))}
      {imageList.length < 10 && (
        <div className="m-auto py-10">
          <FileUploader
            handleChange={imageRegistHandler}
            name="file"
            types={fileTypes}
            multiple
            onDraggingStateChange={(dragging: boolean) => setIsDrag(dragging)}>
            <button
              type="button"
              className="w-32 h-32 items-center rounded-lg bg-opacity-60 outline-dashed outline-4 outline-BACKGROUND-300 bg-BACKGROUND-100">
              {!isDrag && <PhotoIcon styleString="w-12 h-12 mx-auto" />}
            </button>
          </FileUploader>
        </div>
      )}
    </ul>
  );
}

// export default ImageUploader;
const MemoizmImageUploader = React.memo(ImageUploader);

export default MemoizmImageUploader;
