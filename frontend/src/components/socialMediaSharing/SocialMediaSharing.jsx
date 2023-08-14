import React from 'react';
import { Helmet } from "react-helmet";
import { 
    FacebookShareButton,
    TwitterShareButton,
    WhatsappShareButton,
    FacebookIcon,
    TwitterIcon,
    WhatsappIcon,
} from 'react-share';

const SocialMediaSharing = ({ message, image, hashtags = []}) => {
    const shareUrl = window.location.href;

    return (
        <div>
            <Helmet>
                <meta property="og:image" content={image} />
            </Helmet>
            <FacebookShareButton
                url={shareUrl}
                quote={message}
                hashtags={hashtags}
            >
                <FacebookIcon size={32} round />
            </FacebookShareButton>

            <TwitterShareButton
                url={shareUrl}
                title={message}
                hashtags={hashtags}
            >
                <TwitterIcon size={32} round />
            </TwitterShareButton>

            <WhatsappShareButton
                url={shareUrl}
                title={message}
            >
                <WhatsappIcon size={32} round />
            </WhatsappShareButton>

        </div>
    );
};

export default SocialMediaSharing;
