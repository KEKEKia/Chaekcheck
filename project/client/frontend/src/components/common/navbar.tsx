import { Outlet } from 'react-router-dom';
import UserDropdown from '../login_page/userdropdown';
import UserLogin from '../login_page/userlogin';
import CheckChaek from '../../assets/images/logo/CheckChaek.png';
import { useAccessToken } from '../../data_source/apiInfo';
import BottomSheet from './bottomSheet';
import { useModal } from '../modal/modalClass';

function Navbar() {
  const token = useAccessToken();
  const { openModal } = useModal();
  const modalName = 'userLogin';

  const handlepredict = () => {
    if (token) {
      window.location.href = '/predict';
    } else {
      openModal(modalName);
    }
  };
  return (
    <>
      <nav className="flex justify-around items-center py-4 px-6 text-xl shadow-md w-full">
        <a href="/">
          <img
            src={CheckChaek}
            alt="CheckChaek"
            className="w-[81px] h-[55px]"
          />
        </a>
        <div className="flex items-center space-x-4">
          <button
            type="button"
            onClick={handlepredict}
            className="rounded-lg px-3 py-2 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900">
            예측
          </button>
          {token && (
            <a
              href="/history"
              className="rounded-lg px-3 py-2 text-slate-700 font-medium hover:bg-slate-100 hover:text-slate-900">
              내 서재
            </a>
          )}
        </div>
        {token ? <UserDropdown /> : <UserLogin />}
      </nav>
      <Outlet />
      <BottomSheet />
    </>
  );
}

export default Navbar;
