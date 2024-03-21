import Goodcover from '../../assets/images/guide/goodcover.jpg';
import Badcover from '../../assets/images/guide/badcover.jpg';
import Goodback from '../../assets/images/guide/goodback.jpg';
import Badback from '../../assets/images/guide/badback.jpg';
import Goodside from '../../assets/images/guide/goodside.jpg';
import Badside from '../../assets/images/guide/badside.jpg';

interface Content {
  okAction: () => void;
}

function Guide({ okAction }: Content) {
  return (
    <div className=" w-[960px] bg-MAIN-50 my-3 pt-16 pb-10 rounded-3xl">
      <p className="text-4xl font-bold text-center ">사진 입력 가이드라인</p>
      <div className="bg-MAIN-100 min-h-[15vh] max-w-[90vh] mx-auto rounded-md text-2xl text-center mt-10 flex flex-col justify-center items-center p-8">
        <p>
          아래의 예시처럼 촬영해야 인공지능이 좀 더 정확한 금액을 알려 줄 수
          있어요
        </p>
      </div>
      <p className="text-3xl font-bold mt-10 text-center ">예시</p>
      <div className="bg-MAIN-100 grid  grid-cols-9 gap-4 min-h-[60vh] max-w-[90vh] mx-auto rounded-md mt-10 p-8">
        <div className="col-start-5 col-span-2 mt-4 ml-4 text-center ">
          옳은 예
        </div>
        <div className="col-start-7 col-span-2 mt-4 ml-16 text-center ">
          나쁜 예
        </div>
        <div className="col-start-1 col-span-4 text-xl flex flex-col ml-4">
          <div className="text-2xl font-bold flex flex-col justify-center items-center mb-4">
            책 면
          </div>
          <div className="flex text-lg">
            <span> - </span>
            <p>책 면이 정확하게 보이도록 찍은 사진</p>
          </div>
          <div className="flex text-lg">
            <span> - </span>
            <p>책 면이 보이는 각도가 일그러지지 않은 사진</p>
          </div>
        </div>
        <img
          src={Goodcover}
          alt="Goodcover"
          className="col-start-5 col-span-2 mx-4"
        />
        <img
          src={Badcover}
          alt="Badcover"
          className="col-start-7 col-span-2 ml-12"
        />
        <div className="col-start-1 col-span-4 text-xl flex flex-col ml-4">
          <div className="text-2xl font-bold flex flex-col justify-center items-center mb-4">
            책 등
          </div>
          <div className="flex text-lg">
            <span> - </span>
            <p>책 등이 정확하게 보이도록 찍은 사진</p>
          </div>
          <div className="flex text-lg">
            <span> - </span>
            <p>책 등이 손이나 다른 물체로 가리지 않은 사진</p>
          </div>
        </div>
        <img
          src={Goodback}
          alt="Goodback"
          className="col-start-5 col-span-2 mx-4"
        />
        <img
          src={Badback}
          alt="Badback"
          className="cols-start-7 col-span-2 ml-12"
        />
        <div className="col-start-1 col-span-4 text-xl flex flex-col ml-4">
          <div className="text-2xl font-bold flex flex-col justify-center items-center mb-4">
            책 옆
          </div>
          <div className="flex text-lg">
            <span> - </span>
            <p>책 옆이 정확하게 보이도록 찍은 사진</p>
          </div>
          <div className="flex text-lg">
            <span> - </span>
            <p>책 옆이 과하게 펼쳐지지 않은 사진</p>
          </div>
        </div>
        <img
          src={Goodside}
          alt="Goodside"
          className="col-start-5 col-span-2 mx-4"
        />
        <img
          src={Badside}
          alt="Badside"
          className="col-start-7 col-span-2 ml-12"
        />
      </div>
      <button
        type="button"
        className="rounded-2xl text-xl mt-10 w-[40%] h-14 py-2 hover:bg-BUTTON1-900 bg-BUTTON1-500 text-BACKGROUND-50"
        onClick={okAction}>
        확인
      </button>
    </div>
  );
}

export default Guide;
