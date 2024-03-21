import { useNavigate } from 'react-router-dom';
import MainBooks from '../assets/images/main_page/books.png';
import Login from '../components/modal/logInContents';
import LeftArrowIcon from '../assets/icons/leftArrowIcon';
import RightArrowIcon from '../assets/icons/rightArrowIcon';
import DoubleDownIcon from '../assets/icons/doubleDownIcon';
import { useAccessToken } from '../data_source/apiInfo';
import { useModal } from '../components/modal/modalClass';

function MainPage() {
  const isLogin = useAccessToken();
  const navigate = useNavigate();
  const { openModal } = useModal();
  const directionHandler = () => {
    if (isLogin) {
      navigate('/predict');
    } else {
      openModal('userLogin');
    }
  };

  return (
    <div className="MainPage">
      <div className="snap-end FirstPage bg-MAIN-200 w-full min-h-[85.5vh] relative">
        <div className="flex justify-center pt-20">
          <img
            className="w-[36vh] h-[60vh] "
            src={MainBooks}
            alt="MainBookImage"
          />
          <div className="ml-32">
            <h3 className="text-5xl font-bold pt-32 text-center text-FONT-50">
              중고책이 얼마라고?
            </h3>
            <div className="flex justify-center">
              <button
                type="submit"
                onClick={directionHandler}
                className="text-2xl text-center font-bold mt-10 text-FONT-50 animate-bounce">
                내 책 예측하러가기
              </button>
            </div>
          </div>
          <DoubleDownIcon styleString="w-8 h-8 absolute left-[50%] bottom-2 animate-[bounce_2s_infinite] text-FONT-50 " />
        </div>
      </div>
      <div className="snap-end SecondPage relative bg-cover bg-second-page w-full h-screen">
        <div className="TextPard bg-BACKGROUND-800 bg-opacity-50 w-full h-full align-middle pt-64">
          <h3 className="text-6xl font-bold  text-center text-FONT-50">
            책장이 혹시...
          </h3>
          <div className="flex justify-center mt-10">
            <button
              type="submit"
              onClick={directionHandler}
              className="text-2xl text-center font-bold mt-10 text-FONT-50 flex justify-center items-center  animate-bounce">
              정리하러 가기
              <RightArrowIcon styleString="w-10 h-10 ml-4" />
            </button>
          </div>
        </div>
        {isLogin ? (
          <div />
        ) : (
          <DoubleDownIcon styleString="w-8 h-8  absolute left-[50%] bottom-2 animate-[bounce_2s_infinite] text-FONT-50 " />
        )}
      </div>
      {isLogin ? (
        <div />
      ) : (
        <div className="snap-center ThirdPagePage w-full h-screen bg-MAIN-300 md:snap-none">
          <div className="LoginPart w-full h-full flex justify-center">
            <Login />
            <div className="TextPart  self-center ml-20">
              <p className="LoginText text-center font-bold text-4xl text-FONT-50 pb-10">
                내 책 예상가격 보러가기
              </p>
              <div className="StartServiceText flex items-center justify-center animate-bounce">
                <LeftArrowIcon styleString="w-10 h-10 text-FONT-50 font-bold mr-4" />
                <p className="LoginText text-center font-bold text-2xl text-FONT-50 ">
                  로그인하고 시작하기
                </p>
              </div>
            </div>
          </div>
        </div>
      )}
      <div className="snap-end" />
    </div>
  );
}

export default MainPage;
