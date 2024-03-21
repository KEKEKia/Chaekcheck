// Router & Hooks
import { useState, useEffect, useCallback, useRef } from 'react';
import { useNavigate } from 'react-router-dom';

// Modals
import Modal from '../components/modal/modal';
import { useModal } from '../components/modal/modalClass';
import AlertContents from '../components/modal/alertContents';
import Guide from '../components/modal/guide';

// Components
import Card from '../components/common/card';
import MemoizmImageSlider from '../components/predict_page/imageSlider';
import MemoizmImageUploader from '../components/predict_page/imageUploader';
import PredictBtn from '../components/common/predictBtn';

function PredictPage() {
  const { modalOpen, openModal, closeModal } = useModal();
  const [imageList, setImageList] = useState<File[]>([]);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
  const [isDrag, setIsDrag] = useState(false);

  const modalName10 = 'imageLimit10';
  const modalName0 = 'imageLimit0';
  const guidemodalName = 'guide';
  const guideRef = useRef(true);
  const handleGuide = useCallback(() => {
    if (guideRef.current) {
      openModal(guidemodalName);
      guideRef.current = false;
    }
  }, [openModal]);

  useEffect(() => {
    handleGuide();
  }, [handleGuide]);

  const navigate = useNavigate();

  const imageRegistHandler = (files: File[]) => {
    const tempImagelist = [...imageList, ...files];

    if (tempImagelist.length > 10) {
      openModal(modalName10);
    } else {
      setImageList(tempImagelist);
    }
  };
  const imageButtonHandler = () => {
    if (imageList.length < 2) {
      openModal(modalName0);
    } else {
      navigate('/result', { state: imageList });
    }
  };

  return (
    <div className="Predict">
      <Card width="w-3/4" height="min-h-[80vh]">
        <div className="ImageSlider">
          <MemoizmImageSlider
            imageRegistHandler={imageRegistHandler}
            imageList={imageList}
            currentImageIndex={currentImageIndex}
            setCurrentImageIndex={setCurrentImageIndex}
            isDrag={isDrag}
            setIsDrag={setIsDrag}
          />
        </div>
        <div className="mt-10  text-lg text-center">
          * 제목이 잘보이는 사진을 제일 앞에 넣어주세요!
        </div>

        <div className="ImageUploader">
          <MemoizmImageUploader
            imageList={imageList}
            setImageList={setImageList}
            currentImageIndex={currentImageIndex}
            setCurrentImageIndex={setCurrentImageIndex}
            isDrag={isDrag}
            setIsDrag={setIsDrag}
            imageRegistHandler={imageRegistHandler}
          />
        </div>
        <div className="flex justify-center my-2">
          <PredictBtn
            width="w-[25rem]"
            height="h-[4rem]"
            defaultColor="bg-BUTTON1-500"
            selectedColor="bg-BUTTON1-900"
            fontColor="text-FONT-50 text-lg"
            action={() => imageButtonHandler()}>
            결과 확인하기
          </PredictBtn>
        </div>
      </Card>
      <Modal
        closeModal={() => closeModal(modalName10)}
        OpenModal={modalOpen[modalName10]}
        width="w-[400px]"
        height="h-60">
        <AlertContents
          content="이미지는 10개까지 입니다."
          okAction={() => closeModal(modalName10)}
        />
      </Modal>
      <Modal
        closeModal={() => closeModal(modalName0)}
        OpenModal={modalOpen[modalName0]}
        width="w-[400px]"
        height="h-60">
        <AlertContents
          content="이미지를 2개이상 넣어주세요."
          okAction={() => closeModal(modalName0)}
        />
      </Modal>

      <Modal
        closeModal={() => closeModal(guidemodalName)}
        OpenModal={modalOpen[guidemodalName]}
        width="w-[960px]"
        height="overflow-auto">
        <Guide okAction={() => closeModal(guidemodalName)} />
      </Modal>
    </div>
  );
}

export default PredictPage;
