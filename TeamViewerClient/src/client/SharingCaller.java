package client;

import commons.Content;
import commons.ContentType;
import commons.MouseDetails;

public class SharingCaller implements ContentType {

        /**
         * ************************************************************
         *
         * This class is use for handling all the method of sharing class (i.e
         * calling )
         *
         *************************************************************
         */
        Sharing share;

        /**
         * ***********************Constructor*****************************
         */
        /**
         *
         * The below constructor is use for initialization
         *
         *
         */
        public SharingCaller(Sharing share) {
                this.share = share;
        }

        /**
         * ***********************METHODS*****************************
         */
        /**
         * The below method is use for manage all the choice whatever come from
         * the access side for eg: if access method send the mouse coordinate
         * then the mouse coordinate method is called
         *
         * @param: Content obj
         * @return:Nothing
                *
         */
        public void process(Content c) {
                int choice = c.getContentType();
                Object content = c.getContent();
                switch (choice) {
                        case ContentType.MOUSEMOVED:
                                share.mouseMovedToShareScreen((MouseDetails) content);
                                break;
                        case ContentType.MOUSEPRESS:
                                share.mousePressToShareScreen((MouseDetails) content);
                                break;
                        case ContentType.MOUSERELEASE:
                                share.mouseReleasedToShareScreen((MouseDetails) content);
                                break;
                        case ContentType.KEYPRESS:
                                share.keyPressToShareScreen((Integer) content);
                                break;
                        case ContentType.KEYRELEASE:
                                share.keyReleasedToShareScreen((Integer) content);
                                break;
                        case ContentType.MESSAGE:
                                share.showMessageOnChat((String) content);
                                break;
                        case ContentType.CLIPBOARDCONTENT:
                                share.setClipboardContent((String) content);
                }
        }
}
