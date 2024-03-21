import Modal from '../modal/modal';
import { useModal } from '../modal/modalClass';
import Login from '../modal/logInContents';

function UserLogin() {
  const { modalOpen, openModal, closeModal } = useModal();
  const modalName = 'userLogin';

  return (
    <div className="relative inline-block text-left">
      <div>
        <button
          type="button"
          onClick={() => openModal(modalName)}
          className="inline-flex w-full justify-center gap-x-1.5 rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
          로그인
        </button>
      </div>

      <Modal
        closeModal={() => closeModal(modalName)}
        OpenModal={modalOpen[modalName]}
        width="w-[400px]"
        height="h-[680px] ">
        <Login />
      </Modal>
    </div>
  );
}

export default UserLogin;
